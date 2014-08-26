package com.scancella.hermes.network.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import com.scancella.hermes.SimpleTest;
import com.scancella.hermes.network.domain.Server;

public class NetworkServerHandlerTest extends SimpleTest
{
  private NetworkServerHandler sut;
  
  private static final ProtocolVersion PROTOCOL_VERSION = new ProtocolVersion("protocol", 1, 1);
  private BasicHttpResponse goodResponse = new BasicHttpResponse(PROTOCOL_VERSION, 200, "reason");
  private static final Server SERVER = new Server("serverName", "127.0.0.1", 80, null);
  
  @Before
  public void setup() throws Exception
  {
    sut = new NetworkServerHandler();
    
    File dummyFile = new ClassPathResource("templates/greeting.html").getFile();
    InputStream dummyInputStream = new FileInputStream(dummyFile);
    BasicHttpEntity entity = new BasicHttpEntity();
    entity.setContent(dummyInputStream);
    goodResponse.setEntity(entity);
  }
  
  @Test(expected=HttpHostConnectException.class)
  public void testGetAdjacentServersFromServerWhenServerDoesntExist() throws Exception
  {
    sut.getAdjacentServersFromServer(SERVER);
  }
  
  @Test
  public void testCheckIsResponseOkWhenOK()
  {
    assertTrue(sut.checkIsResponseOk(goodResponse));
  }
  
  @Test(expected=RuntimeException.class)
  public void testCheckIsResponseOkWhenNotOk()
  {
    ProtocolVersion pv = new ProtocolVersion("protocol", 1, 1);    
    HttpResponse response = new BasicHttpResponse(pv, 404, "reason");
    assertFalse(sut.checkIsResponseOk(response));
  }
  
  @Test
  public void testSendRequest() throws Exception
  {
    HttpClient mockClient = Mockito.mock(HttpClient.class);
    
    Mockito.when(mockClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(goodResponse);
    
    InputStream is = sut.sendRequest(mockClient, SERVER);
    assertNotNull(is);
  }
}
