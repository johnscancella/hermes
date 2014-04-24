package com.scancella.hermes.services;

import com.scancella.hermes.core.Configuration;
import com.scancella.hermes.core.Supportable;


/**
 * Used to save and restore server configurations.
 */
public interface ConfigurationManager extends Supportable<Configuration>
{
  public boolean save(Configuration config);
  public boolean restore(Configuration config);
}
