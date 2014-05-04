package com.scancella.hermes.network.domain;

public class AddAccountResponse
{
  private final String message;
  private final boolean successfullyAddedAccount;

  public AddAccountResponse(boolean successfullyAddedAccount, String reponseMessage)
  {
    this.message = reponseMessage;
    this.successfullyAddedAccount = successfullyAddedAccount;
  }

  public static AddAccountResponse createDefaultSuccess(Account account, String serverName)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Successfully added account ").append(account);
    sb.append(" to ").append(serverName);

    return new AddAccountResponse(true, sb.toString());
  }
  
  public static AddAccountResponse createDefaultFailure(Account account, String serverName)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Did not successfully add account ").append(account);
    sb.append(" to ").append(serverName);

    return new AddAccountResponse(false, sb.toString());
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
