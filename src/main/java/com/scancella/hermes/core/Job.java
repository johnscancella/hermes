package com.scancella.hermes.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job implements Runnable
{
  @Override
  public void run()
  {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    logger.error("GREETINGS FROM JOB DETAIL!!!!");
  }
}
