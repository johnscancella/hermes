package com.scancella.hermes.domain;

public enum RestServices
{
  ROUTE("route", "route.do"), //TODO evaluate if I still need this one...
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
