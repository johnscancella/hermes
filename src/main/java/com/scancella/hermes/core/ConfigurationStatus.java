package com.scancella.hermes.core;

public class ConfigurationStatus
{
  private boolean statusOk;
  private String statusMessage;

  public boolean isStatusOk()
  {
    return statusOk;
  }

  public void setStatusOk(boolean statusOk)
  {
    this.statusOk = statusOk;
  }

  public String getStatusMessage()
  {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage)
  {
    this.statusMessage = statusMessage;
  }
}
