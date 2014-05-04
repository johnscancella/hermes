package com.scancella.hermes.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.mappers.JsonMapper;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.AddAccountResponse;
import com.scancella.hermes.network.domain.AddOpenPortResponse;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.domain.ServerMetadata;

/**
 * Provides a rest interface for querying about the network for this server.
 */
@RestController
public class NetworkRestController extends LoggingObject
{
  private Map<String, Server> adjacentServers;
  private Map<Server, ServerMetadata> serverMetadata;
  
  @Autowired
  private JsonMapper<Server> jsonServerMapper;
  
  @PostConstruct
  public void init()
  {
    adjacentServers = new HashMap<>();
    serverMetadata = new HashMap<>();
  }
  
  @RequestMapping("getAdjacentServers.do")
  public String getAdjacentServers() 
  {    
    return jsonServerMapper.toJson(adjacentServers.values());
  }
  
  //TODO change to ensure multiple users can add adjacent servers at the same time
  @RequestMapping("/addAdjacentServer.do")
  public boolean addAdjacentServer(@RequestParam(value="name", required=true) String serverName, @RequestParam(value="ip", required=true) String ipAddress) 
  {    
    Server adjacentServer = new Server(serverName, ipAddress);
    adjacentServers.put(serverName, adjacentServer);
    logger.debug("Added server: [name=" + serverName + "] [ip=" + ipAddress + "]");
    
    return true;
  }
  
  //TODO change to ensure multiple users can add accounts at the same time
  @RequestMapping("/addServerAccount.do")
  public AddAccountResponse addServerAccount(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="accountname", required=true) String accountName,
      @RequestParam(value="accountpassword", required=true) String accountPassword) 
  {
    Account account = new Account(accountName, accountPassword);
    return addAccountToServer(serverName, account);
  }
  
  protected AddAccountResponse addAccountToServer(String serverName, Account account)
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
    if(serverMetadata.containsKey(adjacentServer))
    {
      serverMetadata.get(adjacentServer).getAccounts().add(account);
    }
    else
    {
      ServerMetadata metaData = new ServerMetadata();
      metaData.getAccounts().add(account);
      serverMetadata.put(adjacentServer, metaData);
    }
  }
  
  //TODO change to ensure multiple users can add open ports at the same time
  @RequestMapping("/addServerAccount.do")
  public AddOpenPortResponse addOpenPort(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="port", required=true) int port) 
  {
    return addOpenPortToServer(serverName, port);
  }
  
  protected AddOpenPortResponse addOpenPortToServer(String serverName, int port)
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
    if(serverMetadata.containsKey(adjacentServer))
    {
      serverMetadata.get(adjacentServer).getOpenPorts().add(port);
    }
    else
    {
      ServerMetadata metaData = new ServerMetadata();
      metaData.getOpenPorts().add(port);
      serverMetadata.put(adjacentServer, metaData);
    }
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
  
}
