package com.scancella.hermes.network.domain;

public class Vertex
{
  private final String name;
  private Edge[] adjacencies;
  private double minDistance = Double.POSITIVE_INFINITY;
  private Vertex previous;

  public Vertex(String argName)
  {
    name = argName;
  }

  @Override
  public String toString()
  {
    return name;
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

  public String getName()
  {
    return name;
  }
}
