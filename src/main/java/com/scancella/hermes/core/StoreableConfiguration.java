package com.scancella.hermes.core;

import org.springframework.core.io.Resource;

/**
 * Marker interface for classes that should be saved to a configuration.
 */
public interface StoreableConfiguration
{
  //TODO have all classes that implement this use a spring properties file to read/write to the same folder. 
  public ConfigurationStatus saveToConfiguration();
  
  public ConfigurationStatus restoreConfiguration();
  
  public Resource getConfigurationResource();
}
