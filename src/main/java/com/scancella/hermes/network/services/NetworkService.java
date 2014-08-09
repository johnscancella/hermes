package com.scancella.hermes.network.services;

import java.util.Collection;
import java.util.List;

import com.scancella.hermes.network.domain.Server;

public interface NetworkService
{
  /**
   * Provides a list of adjacent server nodes in the network to the specified server
   */
  public Collection<Server> getAdjacentServers(Server node) throws Exception;
  
  /**
   * Returns the shortest route from one server to another.
   */
  public List<Server> getShortestRoute(Server from, String destinationServerName);
  
  /**
   * Returns the shortest route from localhost to another.
   */
  public List<Server> getShortestRoute(String destinationServerName);
}
