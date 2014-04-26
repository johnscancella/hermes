package com.scancella.hermes.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.domain.Server;
import com.scancella.hermes.mappers.JsonMapper;

/**
 * Provides a rest interface for querying about the network for this server.
 */
@RestController
public class NetworkRestController extends LoggingObject
{
  private List<Server> adjacentServers;
  
  @Autowired
  private JsonMapper<Server> jsonServerMapper;
  
  @PostConstruct
  public void init()
  {
    adjacentServers = new ArrayList<>();
  }
  
  @RequestMapping("getAdjacentServers.do")
  public String getAdjacentServers() 
  {    
    return jsonServerMapper.toJson(adjacentServers);
  }
  
  @RequestMapping("/addAdjacentServer")
  public boolean addAdjacentServer(@RequestParam(value="name", required=true) String serverName, @RequestParam(value="ip", required=true) String ipAddress) 
  {    
    adjacentServers.add(new Server(serverName, ipAddress));
    logger.debug("Added server: [name=" + serverName + "] [ip=" + ipAddress + "]");
    
    return true;
  }
  
}
