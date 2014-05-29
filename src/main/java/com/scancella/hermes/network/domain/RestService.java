package com.scancella.hermes.network.domain;

public enum RestService
{
  GET_ADJACENT_SERVERS("getAdjacentServers", "getAdjacentServers.do"),
  ADD_ADJACENT_SERVER("addAdjacentServer", "addAdjacentServer.do"),
  ADD_SERVER_ACCOUNT("addServerAccount", "addServerAccount.do"),
  SET_SERVER_PORT("setServerPort","setServerPort.do");
  
  private final String name;
  private final String service;
  
  private RestService(String name, String service)
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
