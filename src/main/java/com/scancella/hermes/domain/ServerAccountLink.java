package com.scancella.hermes.domain;

public class ServerAccountLink
{
  private Server server;
  private Account account;
  
  public ServerAccountLink()
  {}
  
  public ServerAccountLink(Server server, Account account)
  {
    this.server = server;
    this.account = account;
  }

  public Server getServer()
  {
    return server;
  }

  public void setServer(Server server)
  {
    this.server = server;
  }

  public Account getAccount()
  {
    return account;
  }

  public void setAccount(Account account)
  {
    this.account = account;
  }
}
