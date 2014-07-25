package com.scancella.hermes.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scancella.hermes.network.domain.Server;

/**
 * Example of how to use a controller. This WILL return thymeleaf tempalte web pages!
 */
@Controller
public class HelloController
{ 
  @RequestMapping("/greeting")
  public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    if(name.equals("World")){
      Server server = new Server("serverName", "ipVersion4");
      Server server2 = new Server("serverName2", "ipVersion4-2");
      Server server3 = new Server("serverName3", "ipVersion4-3");
      model.addAttribute("allServers", Arrays.asList(server, server2, server3));
    }
    else{
      model.addAttribute("allServers", new ArrayList<Server>());
    }
      model.addAttribute("name", name);
      return "greeting";
  } 
}
