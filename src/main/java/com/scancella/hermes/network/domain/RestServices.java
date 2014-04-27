package com.scancella.hermes.network.domain;

public enum RestServices
{
  GET_ADJACENT_SERVERS("getAdjacentServers", "getAdjacentServers.do"),
  ADD_ADJACENT_SERVER("addAdjacentServer", "addAdjacentServer.do");
  
  private final String name;
  private final String service;
  
  private RestServices(String name, String service)
  {
    this.name = name;
    this.service = service;
  }

  public final String getName()
  {
    return name;
  }

  public final String getService()
  {
    return service;
  }  
}
