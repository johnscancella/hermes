package com.scancella.hermes.network.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@XmlRootElement(name="server")
@XmlAccessorType (XmlAccessType.FIELD)
public class Server implements Comparable<Server>
{
  private String name;
  private String ipVersion4;
  @XmlElement(name = "port")
  private Integer fileTransferPort;
  @XmlElement(name = "account")
  private Account account;

  public Server(){}

  public Server(String serverName, String ipVersion4)
  {
    this.name = serverName;
    this.ipVersion4 = ipVersion4;
  }
  
  public Server(String serverName, String ipVersion4, Integer fileTransferPort, Account account)
  {
    this.name = serverName;
    this.ipVersion4 = ipVersion4;
    this.fileTransferPort = fileTransferPort;
    this.account = account;
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
  
  public Integer getFileTransferPort()
  {
    return fileTransferPort;
  }

  public void setFileTransferPort(Integer fileTransferPort)
  {
    this.fileTransferPort = fileTransferPort;
  }

  public Account getAccount()
  {
    return account;
  }

  public void setAccount(Account account)
  {
    this.account = account;
  }

  @Override
  public int compareTo(Server o)
  {
    return this.getIpVersion4().compareTo(o.getIpVersion4());
  }
}
