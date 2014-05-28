package com.scancella.hermes.network.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="servers")
@XmlAccessorType (XmlAccessType.FIELD)
public class Servers
{
  @XmlElement(name = "servers")
  private List<Server> servers = new ArrayList<>();
  
  public Servers(){}
  
  public Servers(Collection<Server> servers)
  {
    this.servers.addAll(servers);
  }

  public List<Server> getServers()
  {
    return servers;
  }

  public void setServers(List<Server> servers)
  {
    this.servers = servers;
  }
}
