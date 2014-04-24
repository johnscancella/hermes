package com.scancella.hermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({"com.scancella.hermes.service", "com.scancella.hermes.controller"})
@EnableAutoConfiguration
@ImportResource({"classpath:META-INF/*-spring.xml"})
public class Main 
{
  public static void main(String[] args)
  {
    SpringApplication.run(Main.class, args);
//    ApplicationContext ctx = SpringApplication.run(Main.class, args);

//    System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//    String[] beanNames = ctx.getBeanDefinitionNames();
//    Arrays.sort(beanNames);
//    for (String beanName : beanNames) {
//        System.out.println(beanName);
//    }
  }
}
