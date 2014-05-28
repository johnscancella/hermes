package com.scancella.hermes.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.core.StoreableConfiguration;
import com.scancella.hermes.mappers.JsonMapper;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.RestService;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.domain.Servers;
import com.scancella.hermes.network.responses.AddAccountResponse;
import com.scancella.hermes.network.responses.AddOpenPortResponse;

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
  public boolean saveToConfiguration()
  {
    logger.debug("Called save configuration!");
    
    try
    {
      JAXBContext jaxbContext = JAXBContext.newInstance(Servers.class);
      Marshaller marshaller = jaxbContext.createMarshaller();
   
      // output pretty printed
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      
      marshaller.marshal(new Servers(adjacentServers.values()), serverConfigResource.getFile());      
      return true;
    }
    catch(Exception e)
    {
      logger.error("Error marshalling servers list", e);
      return false;
    }
  }

  @Override
  public boolean restoreConfiguration()
  {
    logger.debug("Called restore configuration!");
    //TODO
    return true;
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
      addAccount(adjacentServer, account);      
      
      logger.debug("Added account " + account + " to adjacent server " + serverName );
      
      return AddAccountResponse.createDefaultSuccess(account, serverName);
    }
    
    return AddAccountResponse.createDoesNotExistFailure(account, serverName);
  }
  
  protected void addAccount(Server adjacentServer, Account account)
  {
    if(adjacentServer.getAccounts() != null)
    {
      adjacentServer.getAccounts().add(account);
    }
    else
    {
      adjacentServer.setAccounts(Arrays.asList(account));
    }
  }
  
  @RequestMapping("/addServerPort.do")
  public AddOpenPortResponse addOpenPort(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="port", required=true) int port) 
  {
    return addOpenPortToServer(serverName, port);
  }
  
  protected synchronized AddOpenPortResponse addOpenPortToServer(String serverName, int port)
  {
    if(adjacentServers.containsKey(serverName))
    {
      Server adjacentServer = adjacentServers.get(serverName);
      addPort(adjacentServer, port);
      
      logger.debug("Added open port " + port + " to adjacent server " + serverName );
      
      return AddOpenPortResponse.createDefaultSuccess(port, serverName);
    }
    
    return AddOpenPortResponse.createDoesNotExistFailure(port, serverName);
  }
  
  protected void addPort(Server adjacentServer, int port)
  {
    if(adjacentServer.getOpenPorts() != null)
    {
      adjacentServer.getOpenPorts().add(port);
    }
    else
    {
      adjacentServer.setOpenPorts(Arrays.asList(port));
    }
  }
  
  @RequestMapping("/services")
  public String getRestServices() 
  {
    StringBuilder sb = new StringBuilder();
    
    RestService[] services = RestService.values();
    sb.append(services[0].getName());
    
    for(int index=1; index < services.length; index++)
    {
      sb.append(",").append(services[index].getName());
    }
    
    return sb.toString();
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
