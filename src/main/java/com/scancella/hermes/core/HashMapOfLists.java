package com.scancella.hermes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashMapOfLists<K, V> implements MapOfLists<K, V>
{
  private Map<K, List<V>> map;

  @Override
  public void put(K key, V value)
  {
    if(map.containsKey(key))
    {
      map.get(key).add(value);
    }
    else
    {
      List<V> values = new ArrayList<>();
      values.add(value);
      map.put(key, values);
    }
  }

  @Override
  public void put(K key, List<V> values)
  {
    if(map.containsKey(key))
    {
      map.get(key).addAll(values);
    }
    else
    {
      map.put(key, values);
    }
  }

  @Override
  public List<V> get(K key)
  {
    return map.get(key);
  }

  @Override
  public Set<K> keySet()
  {
    return map.keySet();
  }

  @Override
  public Collection<List<V>> values()
  {
    return map.values();
  }

  @Override
  public boolean containsKey(Object key)
  {
    return map.containsKey(key);
  }

}
