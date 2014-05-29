package com.scancella.hermes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.network.domain.RestService;

@RestController
public class ServicesController extends LoggingObject
{  
  @RequestMapping("/services")
  public String getRestServices() 
  {
    StringBuilder sb = new StringBuilder();
    
    RestService[] services = RestService.values();
    sb.append(services[0].getName());
    
    for(int index=1; index < services.length; index++)
    {
      sb.append(",").append(services[index].getName());
    }
    
    return sb.toString();
  }

}
