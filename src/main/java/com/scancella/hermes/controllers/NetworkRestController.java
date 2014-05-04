package com.scancella.hermes.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.core.HashMapOfLists;
import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.core.MapOfLists;
import com.scancella.hermes.mappers.JsonMapper;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.AddAccountResponse;
import com.scancella.hermes.network.domain.Server;

/**
 * Provides a rest interface for querying about the network for this server.
 */
@RestController
public class NetworkRestController extends LoggingObject
{
  private Map<String, Server> adjacentServers;
  private MapOfLists<Server, Account> serverAccounts;
  
  @Autowired
  private JsonMapper<Server> jsonServerMapper;
  
  @PostConstruct
  public void init()
  {
    adjacentServers = new HashMap<>();
    serverAccounts = new HashMapOfLists<>();
  }
  
  @RequestMapping("getAdjacentServers.do")
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
  
  protected AddAccountResponse addAccountToServer(String serverName, Account account)
  {
    if(adjacentServers.containsKey(serverName))
    {
      Server adjacentServer = adjacentServers.get(serverName);

      serverAccounts.put(adjacentServer, account);
      
      logger.debug("Added account " + account + " to adjacent server " + serverName );
      return AddAccountResponse.createDefaultSuccess(account, serverName);
    }
    
    return AddAccountResponse.createDefaultFailure(account, serverName);
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
