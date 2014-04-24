package com.scancella.hermes.domain;

import java.util.List;

import org.springframework.util.Assert;

/**
 * Stores a route from one server to another.
 */
public class Route
{
  private List<ServerAccountLink> serverAccountLinks;
  
  public Route(List<ServerAccountLink> serverAccountLinks)
  {
    Assert.isTrue(serverAccountLinks != null, "The list of Servers cannot be null!");
    this.serverAccountLinks = serverAccountLinks; 
  }

  public List<ServerAccountLink> getServerAccountLinks()
  {
    return serverAccountLinks;
  }

  public void setServerAccountLinks(List<ServerAccountLink> serverAccountLinks)
  {
    this.serverAccountLinks = serverAccountLinks;
  }
  
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Routing Path: \n");
    
    for(int index=0; index<serverAccountLinks.size(); index++)
    {
      for(int numberOfTabs=0; numberOfTabs<=index; numberOfTabs++)
      {
        //indent
        sb.append("  ");
      }
      sb.append("->[");
      sb.append(serverAccountLinks.get(index).getAccount().getUsername());
      sb.append("@");
      sb.append(serverAccountLinks.get(index).getServer().getName());
      sb.append("]\n");
    }
    
    return sb.toString();
  }
  
  public String toStringOneLine()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Routing Path:");
    
    for(int index=0; index<serverAccountLinks.size(); index++)
    {
      sb.append(" ->[");
      sb.append(serverAccountLinks.get(index).getAccount().getUsername());
      sb.append("@");
      sb.append(serverAccountLinks.get(index).getServer().getName());
      sb.append("]");
    }
    
    return sb.toString();
  }
}
