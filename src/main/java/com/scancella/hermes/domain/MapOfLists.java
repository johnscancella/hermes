package com.scancella.hermes.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface MapOfLists<K,V> 
{
  /**
   * adds a value to the list of values associated with the key. </br>
   * or creates a new list that contains only the value
   */
  public void put(K key, V value);
  
  /**
   * Adds the values to the list of values associated with the key
   */
  public void put(K key, List<V> values);
  
  public List<V> get(K key);
  
  public Set<K> keySet();
  
  public Collection<List<V>> values();
  
  boolean containsKey(Object key);
}
