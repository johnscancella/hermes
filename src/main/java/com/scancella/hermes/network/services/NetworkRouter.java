package com.scancella.hermes.network.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.exceptions.NetworkPathNotFound;
import com.scancella.hermes.network.domain.Edge;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.domain.Vertex;

/**
 * Implements Dijkstra's shortest path algorithm
 */
@Component
public class NetworkRouter extends LoggingObject
{
  @Autowired
  private NetworkServerHandler networkServerHandler;
  
  private static final Server LOCALHOST = new Server("localhost", "127.0.0.1", null, null);

  public List<Server> computeShortestPath(Server source, String destinationServerName) throws NetworkPathNotFound
  {
    Map<Server, Vertex> vertices = constructNetwork(source);
    Server to = getServerFromMap(destinationServerName, vertices);
    return computeShortestPath(vertices.get(source), vertices.get(to));
  }
  
  public List<Server> computeShortestPathFromLocalhost(String destinationServerName) throws NetworkPathNotFound
  {
    Map<Server, Vertex> vertices = constructNetwork(LOCALHOST);
    Server to = getServerFromMap(destinationServerName, vertices);
    return computeShortestPath(vertices.get(LOCALHOST), vertices.get(to));
  }
  
  protected Map<Server, Vertex> constructNetwork(Server source)
  {
    Map<Server, Vertex> vertices = new HashMap<>();
    Map<Server, Boolean> visitedServers = new HashMap<>();
    PriorityQueue<Server> serverQueue = new PriorityQueue<>();
    
    serverQueue.add(source);
    while(!serverQueue.isEmpty())
    {
      Server currentServer = serverQueue.poll();
      if( visitedServers.get(currentServer) == null || visitedServers.get(currentServer) == false )
      {
        try
        {
          Collection<Server> adjacentServers = networkServerHandler.getAdjacentServersFromServer(currentServer);
          updateNetworkMapAndQueue(serverQueue, vertices, currentServer, adjacentServers);
        }
        catch(Exception e)
        {
          logger.warn("Could not get adjacent server list for " + currentServer);
          logger.error("Error is: ", e);
        }
        
        // mark the server as visited so we don't get endless loops
        visitedServers.put(currentServer, true);
      }
    }
    
    return vertices;
  }
  
  protected Server getServerFromMap(String serverName, Map<Server, Vertex> map)
  {
    for(Server server : map.keySet())
    {
      if( server.getName().equals(serverName) || server.getIpVersion4().equals(serverName))
      {
        return server;
      }
    }
    
    return null;
  }

  //TODO something is wrong here, each vertex only ever has one adjacent edge which is wrong...
  protected void updateNetworkMapAndQueue(PriorityQueue<Server> serverQueue, Map<Server, Vertex> vertices,
      Server currentServer, Collection<Server> adjacentServers)
  {
    Vertex currentServerVertex = getVertexFromMap(currentServer, vertices);
    for(Server adjacentServer : adjacentServers)
    {
      Vertex adjacentVertex = getVertexFromMap(adjacentServer, vertices);
      // add an edge back from adjacent server to the current server
      Edge currentServerEdge = new Edge(currentServerVertex, 1.0);
      adjacentVertex.getAdjacencies().add(currentServerEdge);
      // add an edge from current server to adjacent server
      Edge adjacentServerEdge = new Edge(adjacentVertex, 1.0);
      currentServerVertex.getAdjacencies().add(adjacentServerEdge);
      serverQueue.add(adjacentServer);
    }
  }

  protected Vertex getVertexFromMap(Server server, Map<Server, Vertex> vertices)
  {
    Vertex serverVertex = null;
    
    if( vertices.get(server) != null )
    {
      serverVertex = new Vertex(server);
      vertices.put(server, serverVertex);
    }
    else
    {
      serverVertex = new Vertex(server);
      vertices.put(server, serverVertex);
    }
    
    return serverVertex;
  }

  /**
   * modified from http://www.algolist.com/code/java/Dijkstra%27s_algorithm
   */
  protected List<Server> computeShortestPath(Vertex source, Vertex to) throws NetworkPathNotFound
  {
    source.setMinDistance(0.0);
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
    
    vertexQueue.add(source);
    while(!vertexQueue.isEmpty())
    {
      Vertex currentVertex = vertexQueue.poll();
      for(Edge edge : currentVertex.getAdjacencies())
      {
        Vertex adjacentVertex = edge.getTarget();
        double distanceThroughCurrentVertex = currentVertex.getMinDistance() + edge.getWeight();
        
        if( adjacentVertex.getServer().getIpVersion4().equals(to.getServer().getIpVersion4()) )
        {
          adjacentVertex.setMinDistance(distanceThroughCurrentVertex);
          adjacentVertex.setPrevious(currentVertex);
          return getShortestPathTo(adjacentVertex);
        }
        if( distanceThroughCurrentVertex < adjacentVertex.getMinDistance() )
        {
          updateMinDistance(vertexQueue, adjacentVertex, currentVertex, distanceThroughCurrentVertex);
        }
      }
    }
    
    throw createNetworkPathNotFound(source, to);
  }
  
  protected void updateMinDistance(PriorityQueue<Vertex> vertexQueue, Vertex adjacentVertex, Vertex currentVertex, double distanceThroughCurrentVertex)
  {
    vertexQueue.remove(adjacentVertex);
    adjacentVertex.setMinDistance(distanceThroughCurrentVertex);
    adjacentVertex.setPrevious(currentVertex);
    vertexQueue.add(adjacentVertex);
  }

  protected List<Server> getShortestPathTo(Vertex target)
  {
    List<Server> path = new ArrayList<>();
    
    for(Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
    {
      path.add(vertex.getServer());
    }
    
    Collections.reverse(path);
    
    return path;
  }

  protected NetworkPathNotFound createNetworkPathNotFound(Vertex source, Vertex to)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Could not find a network path from ");
    sb.append(source.getServer()).append("to ").append(to.getServer());
    return new NetworkPathNotFound(sb.toString());
  }

  public NetworkServerHandler getNetworkServerHandler()
  {
    return networkServerHandler;
  }

  public void setNetworkServerHandler(NetworkServerHandler adjacentServerHandler)
  {
    this.networkServerHandler = adjacentServerHandler;
  }
}
