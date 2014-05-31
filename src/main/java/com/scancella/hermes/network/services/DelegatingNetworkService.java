package com.scancella.hermes.network.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.exceptions.NetworkPathNotFound;
import com.scancella.hermes.network.domain.Server;

public class DelegatingNetworkService extends LoggingObject implements NetworkService
{  
  @Autowired
  private AdjacentServerHandler adjacentServerHandler;
  
  @Autowired
  private NetworkRouter networkRouter;
  
  @Override
  public Collection<Server> getAdjacentServers(Server node) throws Exception
  {
    return adjacentServerHandler.getAdjacentServers(node);
  }

  @Override
  public List<Server> getShortestRoute(Server from, Server to)
  {
    try
    {
      return networkRouter.computeShortestPath(from, to);
    }
    catch(NetworkPathNotFound e)
    {
      logger.error("Returning an empty path, cause no path found!", e);
      return new ArrayList<>();
    }
  }
}
