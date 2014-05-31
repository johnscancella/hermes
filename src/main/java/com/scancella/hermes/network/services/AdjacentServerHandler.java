package com.scancella.hermes.network.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scancella.hermes.network.domain.RestService;
import com.scancella.hermes.network.domain.Server;

@Component
public class AdjacentServerHandler
{ 
  public Collection<Server> getAdjacentServers(Server server) throws ClientProtocolException, IOException
  {
    InputStream stream = sendRequestForAdjacentServers(server);
    return parseServers(stream);
  }
  
  protected InputStream sendRequestForAdjacentServers(Server server) throws ClientProtocolException, IOException
  {
    DefaultHttpClient httpClient = new DefaultHttpClient();
    
    InputStream contentStream = sendRequest(httpClient, server);
    
    httpClient.getConnectionManager().shutdown();
    
    return contentStream;
  }
  
  protected InputStream sendRequest(HttpClient httpClient, Server server) throws ClientProtocolException, IOException
  {
    HttpGet getRequest = new HttpGet(constructHttpGet(server));
    getRequest.addHeader("accept", "application/json");
    
    HttpResponse response = httpClient.execute(getRequest);
    
    checkIsResponseOk(response);
    
    return response.getEntity().getContent();
  }
  
  protected String constructHttpGet(Server server)
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("http://").append(server.getIpVersion4()).append(":").append(server.getFileTransferPort());
    sb.append("/").append(RestService.GET_ADJACENT_SERVERS.getService());
    
    return sb.toString();
  }
  
  protected boolean checkIsResponseOk(HttpResponse response)
  {
    if( response.getStatusLine().getStatusCode() != 200 )
    {
      throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
    }
    
    return true;
  }
  
  protected Collection<Server> parseServers(InputStream responseContent) throws IOException, JsonParseException, JsonMappingException
  {
    ObjectMapper mapper = new ObjectMapper();
    Collection<Server> servers = mapper.readValue(responseContent, new TypeReference<Collection<Server>>(){});
    
    return servers;
  }
}
