package com.scancella.hermes.network.responses;

public class AddOpenPortResponse
{
  private final String message;
  private final boolean successfullyAddedAccount;

  public AddOpenPortResponse(boolean successfullyAddedAccount, String reponseMessage)
  {
    this.message = reponseMessage;
    this.successfullyAddedAccount = successfullyAddedAccount;
  }

  public static AddOpenPortResponse createDefaultSuccess(int port, String serverName)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Successfully added open Port ").append(port);
    sb.append(" to ").append(serverName);

    return new AddOpenPortResponse(true, sb.toString());
  }
  
  public static AddOpenPortResponse createDoesNotExistFailure(int port, String serverName)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Did not successfully add open port ").append(port);
    sb.append(" to ").append(serverName);
    sb.append(" because it does not exist");

    return new AddOpenPortResponse(false, sb.toString());
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
