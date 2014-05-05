package com.scancella.hermes.core;

/**
 * Marker interface for classes that should be saved to a configuration.
 */
public interface StoreableConfiguration
{
  public void saveToConfiguration();
  
  public void restoreConfiguration();
}
