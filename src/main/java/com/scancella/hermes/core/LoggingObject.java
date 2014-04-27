package com.scancella.hermes.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingObject
{
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  public Logger getLogger()
  {
    return logger;
  }

}
