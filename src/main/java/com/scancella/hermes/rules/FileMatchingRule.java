package com.scancella.hermes.rules;


/**
 * Used to create a routing rule for any file received. Any file in the scan directory that matches the regex will be send to the specified server.
 */
public class FileMatchingRule
{
  private final String regexExpression;
  private final String scanDirectory;
  private final String destinationServer;
  
  public FileMatchingRule(String fileMatchingRegex, String scanDirectory, String destinationServer)
  {
    this.regexExpression = fileMatchingRegex;
    this.scanDirectory = scanDirectory;
    this.destinationServer = destinationServer;
  }

  public String getRegexExpression()
  {
    return regexExpression;
  }

  public String getScanDirectory()
  {
    return scanDirectory;
  }

  public String getDestinationServer()
  {
    return destinationServer;
  }
}
