package com.scancella.hermes.network.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.scancella.hermes.network.domain.Edge;
import com.scancella.hermes.network.domain.Vertex;

/**
 * Implements Dijkstra's shortest path algorithm 
 */
public class NetworkPathUtil
{
  public static void computePaths(Vertex source)
  {
    source.setMinDistance(0.0);
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);
    while(!vertexQueue.isEmpty())
    {
      Vertex u = vertexQueue.poll();
      // Visit each edge exiting u
      for(Edge e : u.getAdjacencies()) //replace u.getAdjacencies with rest call here.
      {
        Vertex v = e.getTarget();
        double weight = e.getWeight();
        double distanceThroughU = u.getMinDistance() + weight;
        if( distanceThroughU < v.getMinDistance() )
        {
          vertexQueue.remove(v);
          v.setMinDistance(distanceThroughU);
          v.setPrevious(u);
          vertexQueue.add(v);
        }
      }
    }
  }
  
  public static List<Vertex> computePath(Vertex source, Vertex endpoint)
  {
    source.setMinDistance(0.0);
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);
    while(!vertexQueue.isEmpty())
    {
      Vertex u = vertexQueue.poll();
      // Visit each edge exiting u
      for(Edge e : u.getAdjacencies())//replace u.getAdjacencies with rest call to get adacent servers here.
      {
        Vertex v = e.getTarget();
        if(v.equals(endpoint))
        {
          return getShortestPathTo(endpoint);
        }
        double weight = e.getWeight();
        double distanceThroughU = u.getMinDistance() + weight;
        if( distanceThroughU < v.getMinDistance() )
        {
          vertexQueue.remove(v);
          v.setMinDistance(distanceThroughU);
          v.setPrevious(u);
          vertexQueue.add(v);
        }
      }
    }
    
    return null;
  }

  public static List<Vertex> getShortestPathTo(Vertex target)
  {
    List<Vertex> path = new ArrayList<Vertex>();
    for(Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
      path.add(vertex);
    Collections.reverse(path);
    return path;
  }
}
