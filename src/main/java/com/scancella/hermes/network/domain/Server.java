package com.scancella.hermes.network.domain;

import java.util.UUID;

public class Server
{
  private final UUID id;
  private String name;
  private String ipVersion4;

  public Server()
  {
    id = UUID.randomUUID();
  }
  
  public Server(String serverName, String ipVersion4)
  {
    id = UUID.randomUUID();
    this.name = serverName;
    this.ipVersion4 = ipVersion4; 
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getIpVersion4()
  {
    return ipVersion4;
  }

  public void setIpVersion4(String ipVersion4)
  {
    this.ipVersion4 = ipVersion4;
  }

  public UUID getId()
  {
    return id;
  }
}
