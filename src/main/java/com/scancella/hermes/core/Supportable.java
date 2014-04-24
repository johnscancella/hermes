package com.scancella.hermes.core;

/**
 * used to define specific supported classes. Usually handlers that only support a specific kind of POJO
 */
public interface Supportable <T>
{
  public void supports(T obj);
}
