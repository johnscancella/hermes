package com.scancella.hermes.network.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.scancella.hermes.SimpleTest;
import com.scancella.hermes.exceptions.NetworkPathNotFound;
import com.scancella.hermes.network.domain.Edge;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.domain.Vertex;

public class NetworkRouterTest extends SimpleTest
{
  private NetworkRouter sut = new NetworkRouter();
  
  @Test
  public void testFindValidPath() throws NetworkPathNotFound
  {
    Server server1 = new Server("server1", null);
    Server server2 = new Server("server2", null);
    Server server3 = new Server("server3", null);
    Server server4 = new Server("server4", null);
    Server server5 = new Server("server5", null);
    
    List<Server> expectedServerPath = Arrays.asList(server1, server2, server5);
    
    Vertex v1 = new Vertex(server1);
    Vertex v2 = new Vertex(server2);
    Vertex v3 = new Vertex(server3);
    Vertex v4 = new Vertex(server4);
    Vertex v5 = new Vertex(server5);
    
    
    v1.setAdjacencies(Arrays.asList(new Edge(v2, 1.0), new Edge(v3, 1.0), new Edge(v4, 1.0)));
    v2.setAdjacencies(Arrays.asList(new Edge(v1, 1.0), new Edge(v5, 1.0)));
    v3.setAdjacencies(Arrays.asList(new Edge(v1, 1.0), new Edge(v4, 1.0)));
    v4.setAdjacencies(Arrays.asList(new Edge(v1, 1.0), new Edge(v3, 1.0), new Edge(v5, 1.0)));
    v5.setAdjacencies(Arrays.asList(new Edge(v2, 1.0), new Edge(v3, 1.0)));
    
    List<Server> path = sut.computeShortestPath(v1, v5);
    assertEquals("The expected should be from server1 to server2 to server5", expectedServerPath, path);
  }
  
  @Test(expected=NetworkPathNotFound.class)
  public void testWhenNoPathExists() throws NetworkPathNotFound
  {
    Server server1 = new Server("server1", null);
    Server server2 = new Server("server2", null);
    Server server3 = new Server("server3", null);
    Server server4 = new Server("server4", null);
    Server server5 = new Server("server5", null);
    
    Vertex v1 = new Vertex(server1);
    Vertex v2 = new Vertex(server2);
    Vertex v3 = new Vertex(server3);
    Vertex v4 = new Vertex(server4);
    Vertex v5 = new Vertex(server5);
    
    
    v1.setAdjacencies(Arrays.asList(new Edge(v2, 1.0), new Edge(v3, 1.0), new Edge(v4, 1.0)));
    v2.setAdjacencies(Arrays.asList(new Edge(v1, 1.0)));
    v3.setAdjacencies(Arrays.asList(new Edge(v1, 1.0), new Edge(v4, 1.0)));
    v4.setAdjacencies(Arrays.asList(new Edge(v1, 1.0), new Edge(v3, 1.0)));
    
    sut.computeShortestPath(v1, v5);
  }
}
