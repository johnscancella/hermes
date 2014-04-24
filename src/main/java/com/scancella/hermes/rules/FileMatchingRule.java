package com.scancella.hermes.rules;

import com.scancella.hermes.domain.Server;

/**
 * Used to create a routing rule for any file received. Any file in the scan directory that matches the regex will be send to the specified server.
 */
public class FileMatchingRule
{
  private final String fileMatchingRegex;
  private final String scanDirectory;
  private final Server destinationServer;
  
  public FileMatchingRule(String fileMatchingRegex, String scanDirectory, Server destinationServer)
  {
    this.fileMatchingRegex = fileMatchingRegex;
    this.scanDirectory = scanDirectory;
    this.destinationServer = destinationServer;
  }

  public String getFileMatchingRegex()
  {
    return fileMatchingRegex;
  }

  public String getScanDirectory()
  {
    return scanDirectory;
  }

  public Server getDestinationServer()
  {
    return destinationServer;
  }
}
