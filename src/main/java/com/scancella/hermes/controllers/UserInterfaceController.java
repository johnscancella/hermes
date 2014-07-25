package com.scancella.hermes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Starting point for the web application
 */
@Controller
public class UserInterfaceController
{
  @RequestMapping("/")
  public String index(Model model) {
    model.addAttribute("name", "John");
    return "hermes";
  }
}
