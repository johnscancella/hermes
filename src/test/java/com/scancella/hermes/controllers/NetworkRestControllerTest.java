package com.scancella.hermes.controllers;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.scancella.hermes.SimpleTest;
import com.scancella.hermes.network.domain.Server;

public class NetworkRestControllerTest extends SimpleTest
{
  private NetworkRestController sut;
  
  @Before
  public void setup()
  {
    sut = new NetworkRestController();
    sut.init();
  }
  
  @After
  public void teardown() throws IOException
  {
    if(sut.getConfigurationResource().exists())
    {
      sut.getConfigurationResource().getFile().delete();
    }
  }
  
  @Test
  public void testSaveServers()
  {
    saveServers();    
    assertTrue(sut.getConfigurationResource().exists());
  }
  
  private void saveServers()
  {
    Server server1 = new Server("foo1", "1.1.1.1");
    sut.getAdjacentServersMap().put("foo1", server1);
    
    Server server2 = new Server("foo2", "1.1.1.1");
    sut.getAdjacentServersMap().put("foo2", server2);
    
    sut.saveToConfiguration();
  }
  
  @Test
  public void testRestoreServers()
  {
    saveServers();
    
    sut = new NetworkRestController();
    sut.init();
    assertEquals(2, sut.getAdjacentServersMap().size());
  }
}
