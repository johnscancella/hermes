package com.scancella.hermes.exceptions;

public class NetworkPathNotFound extends Exception
{
  private static final long serialVersionUID = 1L;

  public NetworkPathNotFound()
  {
    super();
  }

  public NetworkPathNotFound(String msg)
  {
    super(msg);
  }

  public NetworkPathNotFound(String message, Throwable cause)
  {
    super(message, cause);
  }

  public NetworkPathNotFound(Throwable cause)
  {
    super(cause);
  }

  protected NetworkPathNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
