package com.scancella.hermes.services;

import java.util.List;

import com.scancella.hermes.domain.Server;

public interface NetworkService
{
  /**
   * Provides a list of adjacent server nodes in the network to the specified server
   */
  public List<Server> getAdjacentServers(Server node) throws Exception;
}
