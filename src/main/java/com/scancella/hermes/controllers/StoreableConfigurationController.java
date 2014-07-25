package com.scancella.hermes.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.domain.ConfigurationStatus;
import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.domain.StoreableConfiguration;

@RestController
public class StoreableConfigurationController extends LoggingObject
{
  @Autowired
  private List<StoreableConfiguration> configs;
  
  @RequestMapping("/saveconfigurations.do")
  public boolean storeConfigurations()
  {
    boolean allSavedCorrectly = true;
    List<ConfigurationStatus> statuses = new ArrayList<>(configs.size());
    logger.info("Saving " + configs.size() + " configuration(s)");
    
    for(StoreableConfiguration config : configs)
    {
      //store the statuses for later pulling of messages, if needed
      ConfigurationStatus status = config.saveToConfiguration();
      allSavedCorrectly = allSavedCorrectly && status.isStatusOk();
      statuses.add(status);
      
    }
    
    logger.info("Finished saving " + configs.size() + " configuration(s)");
    return allSavedCorrectly;
  }

}
