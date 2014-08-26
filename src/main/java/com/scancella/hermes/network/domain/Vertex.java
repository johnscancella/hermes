package com.scancella.hermes.network.domain;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex>
{
  private final Server server;
  private List<Edge> adjacencies = new ArrayList<>();
  private double minDistance = Double.POSITIVE_INFINITY;
  private Vertex previous;

  public Vertex(Server server)
  {
    this.server = server;
  }

  @Override
  public String toString()
  {
    return server.getName();
  }

  @Override
  public int compareTo(Vertex other)
  {
    return Double.compare(minDistance, other.minDistance);
  }

  public List<Edge> getAdjacencies()
  {
    return adjacencies;
  }

  public void setAdjacencies(List<Edge> adjacencies)
  {
    this.adjacencies = adjacencies;
  }

  public double getMinDistance()
  {
    return minDistance;
  }

  public void setMinDistance(double minDistance)
  {
    this.minDistance = minDistance;
  }

  public Vertex getPrevious()
  {
    return previous;
  }

  public void setPrevious(Vertex previous)
  {
    this.previous = previous;
  }

  public Server getServer()
  {
    return server;
  }
}
