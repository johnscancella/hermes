package com.scancella.hermes.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggingObject
{
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  public Logger getLogger()
  {
    return logger;
  }

}
