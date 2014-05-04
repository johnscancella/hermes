package com.scancella.hermes.network.domain;

import java.util.List;

/**
 * Data associated with a particular server
 */
public class ServerMetadata
{
  private List<Integer> openPorts;
  private List<Account> accounts;

  public List<Integer> getOpenPorts()
  {
    return openPorts;
  }

  public void setOpenPorts(List<Integer> openPorts)
  {
    this.openPorts = openPorts;
  }

  public List<Account> getAccounts()
  {
    return accounts;
  }

  public void setAccounts(List<Account> accounts)
  {
    this.accounts = accounts;
  }
}
