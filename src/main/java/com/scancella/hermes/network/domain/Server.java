package com.scancella.hermes.network.domain;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Server
{
  private String name;
  private String ipVersion4;
  private List<Integer> openPorts;
  private List<Account> accounts;

  public Server(){}

  public Server(String serverName, String ipVersion4)
  {
    this.name = serverName;
    this.ipVersion4 = ipVersion4;
  }
  
  public Server(String serverName, String ipVersion4, List<Integer> openPorts, List<Account> accounts)
  {
    this.name = serverName;
    this.ipVersion4 = ipVersion4;
    this.openPorts = openPorts;
    this.accounts = accounts;
  }

  @Override
  public boolean equals(Object o)
  {
    if( !(o instanceof Server) )
      return false;

    Server that = (Server) o;
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
  
  public List<Integer> getOpenPorts()
  {
    return openPorts;
  }

  public void setOpenPorts(List<Integer> openPorts)
  {
    this.openPorts = openPorts;
  }

  public List<Account> getAccounts()
  {
    return accounts;
  }

  public void setAccounts(List<Account> accounts)
  {
    this.accounts = accounts;
  }
}
