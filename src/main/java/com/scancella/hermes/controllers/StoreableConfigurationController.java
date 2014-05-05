package com.scancella.hermes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.core.StoreableConfiguration;

@RestController
public class StoreableConfigurationController extends LoggingObject
{
  @Autowired
  private List<StoreableConfiguration> configs;
  
  @RequestMapping("/saveconfigurations.do")
  public void storeConfigurations()
  {
    logger.info("Saving " + configs.size() + " configuration(s)");
    
    for(StoreableConfiguration config : configs)
    {
      config.saveToConfiguration();
    }
    
    logger.info("Finished saving " + configs.size() + " configuration(s)");
  }

}
