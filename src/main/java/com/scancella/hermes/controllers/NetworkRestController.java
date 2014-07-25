package com.scancella.hermes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.responses.AddAccountResponse;
import com.scancella.hermes.network.responses.setFileTransferPortResponse;
import com.scancella.hermes.services.ServerManager;

/**
 * Provides a rest interface for querying about the network for this server, and managing that network.
 */
@RestController
public class NetworkRestController extends LoggingObject
{  
  @Autowired
  private ServerManager serverManager;
  
  @RequestMapping("/getAdjacentServers.do")
  public String getAdjacentServers() throws JsonProcessingException 
  {    
    ObjectMapper mapper = new ObjectMapper();
    
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(serverManager.getAllServers());
  }
  
  @RequestMapping("/addAdjacentServer.do")
  public boolean addAdjacentServer(@RequestParam(value="name", required=true) String serverName, @RequestParam(value="ip", required=true) String ipAddress) 
  {    
    Server adjacentServer = new Server(serverName, ipAddress);
    return serverManager.addServer(adjacentServer);
  }
  
  @RequestMapping("/addServerAccount.do")
  public AddAccountResponse addServerAccount(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="accountname", required=true) String accountName,
      @RequestParam(value="accountpassword", required=true) String accountPassword) 
  {
    Account account = new Account(accountName, accountPassword);
    return serverManager.addAccountToServer(serverName, account);
  }
  
  @RequestMapping("/setServerPort.do")
  public setFileTransferPortResponse setFileTransferPort(@RequestParam(value="servername", required=true) String serverName, 
      @RequestParam(value="port", required=true) int port) 
  {
    return serverManager.setFileTransferPort(serverName, port);
  }
}
