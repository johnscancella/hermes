package com.scancella.hermes.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.scancella.hermes.RestfulTest;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.responses.AddAccountResponse;
import com.scancella.hermes.network.responses.setFileTransferPortResponse;
import com.scancella.hermes.services.LocalhostServerManager;

public class NetworkRestControllerTest extends RestfulTest
{
  private NetworkRestController sut;
  
  @Mock
  private LocalhostServerManager mockLocalhostServerManager;

  @Override
  public Object getSut()
  {
    sut = new NetworkRestController();
    sut.setServerManager(mockLocalhostServerManager);
    return sut;
  }
  
  @Test
  public void testGetAdjacentServers() throws Exception
  {
    Server server = new Server("serverName", "ipVersion4");
    
    List<Server> servers = Arrays.asList(server);
    Mockito.when(mockLocalhostServerManager.getAllServers()).thenReturn(servers);
    
    URI uri = new URI("/getAdjacentServers.do");
    mockMvc.perform(get(uri))
    .andExpect(status().isOk())
    .andExpect(content().string("[ {\r\n" + 
        "  \"name\" : \"serverName\",\r\n" + 
        "  \"ipVersion4\" : \"ipVersion4\",\r\n" + 
        "  \"fileTransferPort\" : null,\r\n" + 
        "  \"account\" : null\r\n" + 
        "} ]"));
  }
  
  @Test
  public void testAddAdjacentServer() throws Exception
  {
    Mockito.when(mockLocalhostServerManager.addServer(Mockito.any(Server.class))).thenReturn(true);
    URI uri = new URI("/addAdjacentServer.do?name=serverName&ip=ipAddress");
    
    mockMvc.perform(get(uri))
    .andExpect(status().isOk())
    .andExpect(content().string("true"));
  }
  
  @Test
  public void testAddServerAccount() throws Exception
  {
    AddAccountResponse response = new AddAccountResponse(true, "successfully added account to server");
    Mockito.when(mockLocalhostServerManager.addAccountToServer(Mockito.anyString(), Mockito.any(Account.class))).thenReturn(response);
    URI uri = new URI("/addServerAccount.do?servername=serverName&accountname=accountName&accountpassword=accountPassword");
    
    mockMvc.perform(get(uri))
    .andExpect(status().isOk())
    .andExpect(content().string("{\"message\":\"successfully added account to server\",\"successfullyAddedAccount\":true}"));
  }
  
  @Test
  public void testSetServerPort() throws Exception
  {
    setFileTransferPortResponse response = new setFileTransferPortResponse(true, "success");
    Mockito.when(mockLocalhostServerManager.setFileTransferPort(Mockito.anyString(), Mockito.anyInt())).thenReturn(response);
    URI uri = new URI("/setServerPort.do?servername=serverName&port=1234");
    
    mockMvc.perform(get(uri))
    .andExpect(status().isOk())
    .andExpect(content().string("{\"message\":\"success\",\"successfullyAddedAccount\":true}"));
  }
}
