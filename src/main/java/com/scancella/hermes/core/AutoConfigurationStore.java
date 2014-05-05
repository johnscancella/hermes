package com.scancella.hermes.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class AutoConfigurationStore extends LoggingObject implements Runnable
{
  @Autowired
  private List<StoreableConfiguration> configs;
  
  @Override
  public void run()
  {
    logger.info("Saving " + configs.size() + " configuration(s)");
    
    for(StoreableConfiguration config : configs)
    {
      config.saveToConfiguration();
    }
    
    logger.info("Finished saving " + configs.size() + " configuration(s)");
  }
  
}
