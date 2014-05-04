package com.scancella.hermes.network.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.mappers.JsonMapper;
import com.scancella.hermes.network.domain.RestServices;
import com.scancella.hermes.network.domain.Server;

public class DefaultNetworkService extends LoggingObject implements NetworkService
{
  //TODO make this configurable
  private static final int PORT = 8080;
  
  @Autowired
  private JsonMapper<Server> jsonServerMapper;
  
  @Override
  public List<Server> getAdjacentServers(Server node) throws Exception
  {
    InputStream responseContent = sendRequestForAdjacentServers(node);
    List<Server> adjacentServers = parseServers(responseContent);
    
    return adjacentServers;
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
    
    sb.append("http://").append(server.getIpVersion4()).append(":").append(PORT);
    sb.append("/").append(RestServices.GET_ADJACENT_SERVERS.getService());
    
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
  
  protected List<Server> parseServers(InputStream responseContent) throws IOException
  {
    List<Server> servers = new ArrayList<>();
    
    BufferedReader br = new BufferedReader(new InputStreamReader(responseContent));
    String output;
    while((output = br.readLine()) != null)
    {
      if(jsonServerMapper.canParseIntoListOfObjects(output))
      {
        servers.addAll(jsonServerMapper.fromJsonToList(output));
      }
      else if(jsonServerMapper.canParseIntoSingleObject(output))
      {
        servers.add(jsonServerMapper.fromJson(output));
      }
      else
      {
        throw new RuntimeException("Could not map to Server object from json output: " + output, null);
      }
    }
    
    return servers;
  }

  @Override
  public List<Server> getShortestRoute(Server from, Server to)
  {
    // TODO use networkRouter class here.
    return null;
  }
}