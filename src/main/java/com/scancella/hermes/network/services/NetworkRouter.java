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
      Vertex u = vertexQueue.poll();
      // Visit each edge exiting u
      for(Edge e : u.getAdjacencies())
      {
        Vertex v = e.getTarget();
        double weight = e.getWeight();
        double distanceThroughU = u.getMinDistance() + weight;
        
        if(v.getServer().equals(to.getServer()))
        {
          v.setMinDistance(distanceThroughU);
          v.setPrevious(u);
          return getShortestPathTo(v);
        }
        if( distanceThroughU < v.getMinDistance() )
        {
          vertexQueue.remove(v);
          v.setMinDistance(distanceThroughU);
          v.setPrevious(u);
          vertexQueue.add(v);
        }
      }
    }
    
    
    throw new NetworkPathNotFound("");
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
  
  protected void throwNetworkPathNotFound(Vertex source, Vertex to) throws NetworkPathNotFound
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("Could not find a network path from ");
    sb.append(source.getServer()).append("to ").append(to.getServer());
    
    throw new NetworkPathNotFound(sb.toString());
  }
}
