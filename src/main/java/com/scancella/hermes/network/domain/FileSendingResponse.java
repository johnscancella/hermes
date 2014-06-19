package com.scancella.hermes.network.domain;

public class FileSendingResponse
{
  private final boolean successful;
  private final String message;

  public FileSendingResponse(boolean successful, String message)
  {
    this.successful = successful;
    this.message = message;
  }

  public boolean isSuccessful()
  {
    return successful;
  }

  public String getMessage()
  {
    return message;
  }
}
