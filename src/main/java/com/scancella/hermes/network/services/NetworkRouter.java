package com.scancella.hermes.network.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.scancella.hermes.exceptions.NetworkPathNotFound;
import com.scancella.hermes.network.domain.Edge;
import com.scancella.hermes.network.domain.Server;
import com.scancella.hermes.network.domain.Vertex;

/**
 * Implements Dijkstra's shortest path algorithm 
 */
public class NetworkRouter
{
  public List<Server> computeShortestPath(Vertex source, Vertex to) throws NetworkPathNotFound
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
        double weight = edge.getWeight();
        double distanceThroughCurrentVertex = currentVertex.getMinDistance() + weight;
        
        if(adjacentVertex.getServer().equals(to.getServer()))
        {
          adjacentVertex.setMinDistance(distanceThroughCurrentVertex);
          adjacentVertex.setPrevious(currentVertex);
          return getShortestPathTo(adjacentVertex);
        }
        if( distanceThroughCurrentVertex < adjacentVertex.getMinDistance() )
        {
          vertexQueue.remove(adjacentVertex);
          adjacentVertex.setMinDistance(distanceThroughCurrentVertex);
          adjacentVertex.setPrevious(currentVertex);
          vertexQueue.add(adjacentVertex);
        }
      }
    }
    
    throw throwNetworkPathNotFound(source, to);
  }
  
  public static List<Server> getShortestPathTo(Vertex target)
  {
    List<Server> path = new ArrayList<>();
    for(Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
    {
      path.add(vertex.getServer());
    }
    
    Collections.reverse(path);
    return path;
  }
  
  protected NetworkPathNotFound throwNetworkPathNotFound(Vertex source, Vertex to)
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("Could not find a network path from ");
    sb.append(source.getServer()).append("to ").append(to.getServer());
    
    return new NetworkPathNotFound(sb.toString());
  }
}
