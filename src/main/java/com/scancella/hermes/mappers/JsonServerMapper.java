package com.scancella.hermes.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scancella.hermes.domain.Server;

@Component
public class JsonServerMapper implements JsonMapper<Server>
{
  @Override
  public String toJson(Server object)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String toJson(List<Server> objects)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean canParseIntoSingleObject(String json)
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean canParseIntoListOfObjects(String json)
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Server fromJson(String json)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Server> fromJsonToList(String json)
  {
    // TODO Auto-generated method stub
    return null;
  }
}
