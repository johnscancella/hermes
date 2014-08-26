package com.scancella.hermes.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Test;

import com.scancella.hermes.RestfulTest;

public class ServiceControllerTest extends RestfulTest
{
  private ServicesController sut;

  @Override
  protected Object getSut()
  {
    sut = new ServicesController();
    return sut;
  }
  
  @Test
  public void testGetRestServices() throws Exception
  {
    URI uri = new URI("/services");
    
    mockMvc.perform(get(uri))
    .andExpect(status().isOk())
    .andExpect(content().string("getAdjacentServers,addAdjacentServer,addServerAccount,setServerPort"));
  }

}
