package com.scancella.hermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({ "com.scancella.hermes.**.services", "com.scancella.hermes.**.controllers", "com.scancella.hermes.mappers" })
@EnableAutoConfiguration
@ImportResource({ "classpath:META-INF/*-spring.xml" })
public class Main
{
  public static void main(String[] args)
  {
    SpringApplication.run(Main.class, args);
  }
}
