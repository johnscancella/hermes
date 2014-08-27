package com.scancella.hermes.domain;

public class JobTriggerInfo
{
  private String cronTriggerExpression;
  private String fileMatchingRegex;
  private String scanDirectory;
  private String destinationServer;
  private String destinationDirectory;

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

  public String getDestinationDirectory()
  {
    return destinationDirectory;
  }

  public void setDestinationDirectory(String destinationDirectory)
  {
    this.destinationDirectory = destinationDirectory;
  }
}
