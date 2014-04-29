package com.scancella.hermes.network.domain;

public class Vertex implements Comparable<Vertex>
{
  private final Server server;
  private Edge[] adjacencies;
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

  public int compareTo(Vertex other)
  {
    return Double.compare(minDistance, other.minDistance);
  }

  public Edge[] getAdjacencies()
  {
    return adjacencies;
  }

  public void setAdjacencies(Edge[] adjacencies)
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
