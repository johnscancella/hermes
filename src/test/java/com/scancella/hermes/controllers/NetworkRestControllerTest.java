package com.scancella.hermes.controllers;

import org.junit.Test;

import com.scancella.hermes.SimpleTest;
import com.scancella.hermes.network.domain.Server;

public class NetworkRestControllerTest extends SimpleTest
{
  private NetworkRestController sut;
  
  @Test
  public void testSaveServers()
  {
    sut = new NetworkRestController();
    sut.init();
    
    Server server1 = new Server("foo1", "1.1.1.1");
    sut.getAdjacentServersMap().put("foo1", server1);
    
    Server server2 = new Server("foo2", "1.1.1.1");
    sut.getAdjacentServersMap().put("foo2", server2);
    
    sut.saveToConfiguration();
  }
}
