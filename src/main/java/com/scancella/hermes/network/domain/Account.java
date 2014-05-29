package com.scancella.hermes.network.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Account to use when connecting to a server
 */
@XmlRootElement(name="account")
public class Account
{
  private String username;
  private String password;
  
  public Account()
  {}
  
  public Account(String username, String password)
  {
    this.username = username;
    this.password = password;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if(!(obj instanceof Account))
      return false;
    
    Account that = (Account)obj;
    EqualsBuilder eb = new EqualsBuilder();
    
    eb.append(this.username, that.getUsername());
    eb.append(this.password, that.getPassword());
    
    return eb.isEquals();
  }
  
  @Override
  public int hashCode()
  {
    HashCodeBuilder hcb = new HashCodeBuilder();
    
    hcb.append(username).append(password);
    
    return hcb.toHashCode();
  }
  
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("[").append(this.username).append(":");
    sb.append(this.password).append("]");

    return sb.toString();
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
}
