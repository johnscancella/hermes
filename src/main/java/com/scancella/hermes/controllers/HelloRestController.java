package com.scancella.hermes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * example of how to use a rest controller. This will not return thymeleaf web pages!
 */
@RestController
public class HelloRestController
{
  @Autowired
  @Qualifier("messageChannel")
  private MessageChannel messageChannel;
  private Logger logger = LoggerFactory.getLogger(HelloRestController.class);
  
  @RequestMapping("/route.do")
  public String echo(@RequestParam(value="route", required=false, defaultValue="DEFAULT ROUTE") String route,
    @RequestParam(value="filename", required=false, defaultValue="DEFAULT FILENAME") String filename) {
    logger.warn("route is[" + route + "]");
    logger.warn("filename is ["+ filename + "]");

    Message<String> helloWorld = new GenericMessage<String>("Hello World");
    messageChannel.send(helloWorld);
    
    return "Route is: " + route + " and filename is: " + filename;
  }
 
  //example of real restful server
  @RequestMapping(value = "/{num1}/add/{num2}", method = RequestMethod.GET)
  public String getGreeting(@PathVariable Integer num1, @PathVariable Integer num2) {
    return num1 + " plus " + num2 + " equals " + (num1 + num2);
  }
}
