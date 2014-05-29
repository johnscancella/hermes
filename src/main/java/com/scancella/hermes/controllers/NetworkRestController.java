package com.scancella.hermes.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.core.ConfigurationStatus;
import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.core.StoreableConfiguration;
import com.scancella.hermes.mappers.JsonMapper;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.domain.Servers;
import com.scancella.hermes.network.responses.AddAccountResponse;
import com.scancella.hermes.network.responses.setFileTransferPortResponse;

/**
 * Provides a rest interface for querying about the network for this server.
 */
@RestController
public class NetworkRestController extends LoggingObject implements StoreableConfiguration
{
  private Map<String, Server> adjacentServers;
  
  @Autowired
  private JsonMapper<Server> jsonServerMapper;
  
  private static final Resource serverConfigResource = new PathResource("servers.xml");
  
  @PostConstruct
  public void init()
  {
    adjacentServers = new HashMap<>();
    restoreConfiguration();
  }
  
  @Override
  public ConfigurationStatus saveToConfiguration()
  {
    logger.debug("Called save configuration!");
    ConfigurationStatus status = new ConfigurationStatus();
    
    try
    {
      JAXBContext jaxbContext = JAXBContext.newInstance(Servers.class);
      Marshaller marshaller = jaxbContext.createMarshaller();
   
      // output pretty printed
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      
      marshaller.marshal(new Servers(adjacentServers.values()), serverConfigResource.getFile());      
      status.setStatusOk(true);
      status.setStatusMessage("Successfully saved Servers to " + serverConfigResource.getFilename());
    }
    catch(Exception e)
    {
      logger.error("Error marshalling servers list", e);
      status.setStatusOk(false);
      status.setStatusMessage("Failed to save Servers to " + serverConfigResource.getFilename());
    }
    
    return status;
  }

  @Override
  public ConfigurationStatus restoreConfiguration()
  {
    logger.debug("Called restore configuration!");
    ConfigurationStatus status = new ConfigurationStatus();
    
    try
    {
      JAXBContext jaxbContext = JAXBContext.newInstance(Servers.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      
      Servers servers = (Servers)unmarshaller.unmarshal(serverConfigResource.getFile());
      
      for(Server server : servers.getServers())
      {
        adjacentServers.put(server.getName(), server);
      }
      
      status.setStatusOk(true);
      status.setStatusMessage("Successfully restored Servers from " + serverConfigResource.getFilename());
    }
    catch(Exception e)
    {
      logger.error("Error unmarshalling servers list", e);
      status.setStatusOk(false);
      status.setStatusMessage("Failed to restore Servers from " + serverConfigResource.getFilename());
    }
    
    return status;
  }
  
  @Override
  public Resource getConfigurationResource()
  {
    return serverConfigResource;
  }
  
  @RequestMapping("/getAdjacentServers.do")
  public String getAdjacentServers() 
  {    
    return jsonServerMapper.toJson(adjacentServers.values());
  }
  
  //TODO split out server accounts from server object so I have no fear of returning servers
  @RequestMapping("/addAdjacentServer.do")
  public boolean addAdjacentServer(@RequestParam(value="name", required=true) String serverName, @RequestParam(value="ip", required=true) String ipAddress) 
  {    
    Server adjacentServer = new Server(serverName, ipAddress);
    adjacentServers.put(serverName, adjacentServer);
    logger.debug("Added server: [name=" + serverName + "] [ip=" + ipAddress + "]");
    
    return true;
  }
  
  @RequestMapping("/addServerAccount.do")
  public AddAccountResponse addServerAccount(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="accountname", required=true) String accountName,
      @RequestParam(value="accountpassword", required=true) String accountPassword) 
  {
    Account account = new Account(accountName, accountPassword);
    return addAccountToServer(serverName, account);
  }
  
  protected synchronized AddAccountResponse addAccountToServer(String serverName, Account account)
  {
    if(adjacentServers.containsKey(serverName))
    {
      Server adjacentServer = adjacentServers.get(serverName);
      adjacentServer.setAccount(account);
      
      logger.debug("Added account " + account + " to adjacent server " + serverName );
      
      return AddAccountResponse.createDefaultSuccess(account, serverName);
    }
    
    return AddAccountResponse.createDoesNotExistFailure(account, serverName);
  }
  
  @RequestMapping("/setServerPort.do")
  public setFileTransferPortResponse setFileTransferPort(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="port", required=true) int port) 
  {
    if(adjacentServers.containsKey(serverName))
    {
      Server adjacentServer = adjacentServers.get(serverName);
      adjacentServer.setFileTransferPort(port);
      
      logger.debug("Added open port " + port + " to adjacent server " + serverName );
      
      return setFileTransferPortResponse.createDefaultSuccess(port, serverName);
    }
    
    return setFileTransferPortResponse.createDoesNotExistFailure(port, serverName);
  }

  public JsonMapper<Server> getJsonServerMapper()
  {
    return jsonServerMapper;
  }

  public void setJsonServerMapper(JsonMapper<Server> jsonServerMapper)
  {
    this.jsonServerMapper = jsonServerMapper;
  }

  public void setAdjacentServers(Map<String, Server> adjacentServers)
  {
    this.adjacentServers = adjacentServers;
  }
  
  public Map<String, Server> getAdjacentServersMap()
  {
    return adjacentServers;
  }
}
