package com.scancella.hermes.domain;

import java.io.Serializable;

import org.springframework.core.io.Resource;

/**
 * POJO for storing the data to send, the route of servers to take, and what directory to store the data in.
 */
public class FileDataPathRoute implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private FileData fileData;
  private Route route;
  private Resource destintationDirectory;
  
  /**
   * Removes the head server from the route.
   */
  public ServerAccountLink popRoute()
  {
    return route.getServerAccountLinks().remove(0);
  }
  
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append(fileData.getFilename());
    sb.append(" to directory ");
    sb.append(destintationDirectory.getFilename());
    sb.append(" using ");
    sb.append(route.toString());
    
    return sb.toString();
  }

  public FileData getFileData()
  {
    return fileData;
  }

  public void setFileData(FileData fileData)
  {
    this.fileData = fileData;
  }

  public Route getRoute()
  {
    return route;
  }

  public void setRoute(Route route)
  {
    this.route = route;
  }

  public Resource getDestintationDirectory()
  {
    return destintationDirectory;
  }

  public void setDestintationDirectory(Resource destintationDirectory)
  {
    this.destintationDirectory = destintationDirectory;
  }
}
