package com.scancella.hermes.mappers;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.network.domain.Server;

@Component
public class JsonServerMapper extends LoggingObject implements JsonMapper<Server>
{
  @Override
  public String toJson(Server object)
  {
    // TODO Auto-generated method stub
    logger.error("toJson(Server object) not yet implemented!");
    return null;
  }

  @Override
  public String toJson(Collection<Server> objects)
  {
    // TODO Auto-generated method stub
    logger.error("toJson(Collection<Server> objects) not yet implemented!");
    return objects.toString();
  }

  @Override
  public boolean canParseIntoSingleObject(String json)
  {
    // TODO Auto-generated method stub
    logger.error("canParseIntoSingleObject(String json) not yet implemented!");
    return false;
  }

  @Override
  public boolean canParseIntoListOfObjects(String json)
  {
    // TODO Auto-generated method stub
    logger.error("canParseIntoListOfObjects(String json) not yet implemented!");
    return false;
  }

  @Override
  public Server fromJson(String json)
  {
    // TODO Auto-generated method stub
    logger.error("fromJson(String json) not yet implemented!");
    return null;
  }

  @Override
  public List<Server> fromJsonToList(String json)
  {
    // TODO Auto-generated method stub
    logger.error("fromJsonToList(String json) not yet implemented!");
    return null;
  }
}
