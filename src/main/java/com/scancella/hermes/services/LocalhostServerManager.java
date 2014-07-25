package com.scancella.hermes.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scancella.hermes.domain.ConfigurationStatus;
import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.domain.StoreableConfiguration;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.responses.AddAccountResponse;
import com.scancella.hermes.network.responses.setFileTransferPortResponse;

/**
 * manages the list of servers that this(localhost) server has access to, and the meta-data associated with those servers.
 * TODO split out server accounts from server object so I have no fear of returning servers
 */
@Component
public class LocalhostServerManager extends LoggingObject implements StoreableConfiguration
{
  private Map<String, Server> adjacentServers;
  private static final Resource serverConfigResource = new PathResource("servers.json");
  
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
      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(serverConfigResource.getFile(), adjacentServers.values());
      
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
    
    if(serverConfigResource.exists())
    {
      status = restoreConfigurationFromResource();
    }
    else
    {
      status.setStatusMessage("No configuration to restore from");
      status.setStatusOk(true);
    }
    
    return status;
  }
  
  protected ConfigurationStatus restoreConfigurationFromResource()
  {
    ConfigurationStatus status = new ConfigurationStatus();
    
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      Collection<Server> servers = mapper.readValue(serverConfigResource.getInputStream(), new TypeReference<Collection<Server>>(){});
      
      for(Server server : servers)
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
  
  public setFileTransferPortResponse setFileTransferPort(String serverName, int port){
    if(adjacentServers.containsKey(serverName))
    {
      Server adjacentServer = adjacentServers.get(serverName);
      adjacentServer.setFileTransferPort(port);
      
      logger.debug("Added open port " + port + " to adjacent server " + serverName );
      
      return setFileTransferPortResponse.createDefaultSuccess(port, serverName);
    }
    
    return setFileTransferPortResponse.createDoesNotExistFailure(port, serverName);
  }
  
  @Override
  public Resource getConfigurationResource()
  {
    return serverConfigResource;
  }
  
  public boolean addServer(Server server){
    adjacentServers.put(server.getName(), server);
    
    logger.debug("Added server: [name=" + server.getName() + "] [ip=" + server.getIpVersion4() + "]");
    
    return true;
  }
  
  public void addServers(List<Server> servers){
    for(Server server : servers)
    {
      adjacentServers.put(server.getName(), server);
    }
  }
  
  public Collection<Server> getAllServers(){
    return adjacentServers.values();
  }
  
  public synchronized AddAccountResponse addAccountToServer(String serverName, Account account)
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
  
  public setFileTransferPortResponse addPortToServer(String serverName, int port){
    if(adjacentServers.containsKey(serverName))
    {
      Server adjacentServer = adjacentServers.get(serverName);
      adjacentServer.setFileTransferPort(port);
      
      logger.debug("Added open port " + port + " to adjacent server " + serverName );
      
      return setFileTransferPortResponse.createDefaultSuccess(port, serverName);
    }
    
    return setFileTransferPortResponse.createDoesNotExistFailure(port, serverName);
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
