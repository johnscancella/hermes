package com.scancella.hermes.network.domain;

import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
  
  @Override
  public boolean equals(Object o)
  {
    if(!(o instanceof Server))
      return false;
    
    Server that = (Server)o;
    EqualsBuilder eb = new EqualsBuilder();
    
    eb.append(this.name, that.getName());
    eb.append(this.getIpVersion4(), that.getIpVersion4());
    
    return eb.isEquals();
  }
  
  @Override
  public int hashCode()
  {
    HashCodeBuilder hcb = new HashCodeBuilder();
    
    hcb.append(this.getName());
    hcb.append(this.getIpVersion4());
    
    return hcb.toHashCode();
  }
  
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("[").append(this.getName()).append(":");
    sb.append(this.getIpVersion4()).append("]");
    
    return sb.toString();
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
