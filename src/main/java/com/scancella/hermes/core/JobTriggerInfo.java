package com.scancella.hermes.core;


//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class JobTriggerInfo
{
  private String cronTriggerExpression;
  private String fileMatchingRegex;
  private String scanDirectory;
  private String destinationServer;

  public String getCronTriggerExpression()
  {
    return cronTriggerExpression;
  }

  public void setCronTriggerExpression(String cronTriggerExpression)
  {
    this.cronTriggerExpression = cronTriggerExpression;
  }

  public String getFileMatchingRegex()
  {
    return fileMatchingRegex;
  }

  public void setFileMatchingRegex(String fileMatchingRegex)
  {
    this.fileMatchingRegex = fileMatchingRegex;
  }

  public String getScanDirectory()
  {
    return scanDirectory;
  }

  public void setScanDirectory(String scanDirectory)
  {
    this.scanDirectory = scanDirectory;
  }

  public String getDestinationServer()
  {
    return destinationServer;
  }

  public void setDestinationServer(String destinationServer)
  {
    this.destinationServer = destinationServer;
  }
}
