package com.scancella.hermes.mappers;

import java.util.Collection;
import java.util.List;

public interface JsonMapper <T>
{
  public String toJson(T object);
  public String toJson(Collection<T> objects);
  
  public boolean canParseIntoSingleObject(String json);
  public boolean canParseIntoListOfObjects(String json);
  
  public T fromJson(String json);
  public List<T> fromJsonToList(String json);
}
