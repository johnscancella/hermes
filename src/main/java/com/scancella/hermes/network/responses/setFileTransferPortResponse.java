package com.scancella.hermes.network.responses;

public class setFileTransferPortResponse
{
  private final String message;
  private final boolean successfullyAddedAccount;

  public setFileTransferPortResponse(boolean successfullyAddedAccount, String reponseMessage)
  {
    this.message = reponseMessage;
    this.successfullyAddedAccount = successfullyAddedAccount;
  }

  public static setFileTransferPortResponse createDefaultSuccess(int port, String serverName)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Successfully added open Port ").append(port);
    sb.append(" to ").append(serverName);

    return new setFileTransferPortResponse(true, sb.toString());
  }
  
  public static setFileTransferPortResponse createDoesNotExistFailure(int port, String serverName)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Did not successfully add open port ").append(port);
    sb.append(" to ").append(serverName);
    sb.append(" because it does not exist");

    return new setFileTransferPortResponse(false, sb.toString());
  }

  public String getMessage()
  {
    return message;
  }

  public boolean isSuccessfullyAddedAccount()
  {
    return successfullyAddedAccount;
  }

}
