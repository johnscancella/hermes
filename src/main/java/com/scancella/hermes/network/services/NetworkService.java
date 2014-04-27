package com.scancella.hermes.network.services;

import java.util.List;

import com.scancella.hermes.network.domain.Server;

public interface NetworkService
{
  /**
   * Provides a list of adjacent server nodes in the network to the specified server
   */
  public List<Server> getAdjacentServers(Server node) throws Exception;
}
