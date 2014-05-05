package com.scancella.hermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.scancella.hermes.core.AutoConfigurationStore;

@Configuration
@ComponentScan({ "com.scancella.hermes.**.services", "com.scancella.hermes.**.controllers", "com.scancella.hermes.mappers" })
@EnableAutoConfiguration
@ImportResource({ "classpath:META-INF/*-spring.xml" })
public class Main
{
  public static void main(String[] args)
  {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
    
    //when we shutdown save everything that can be saved to their configurations
    AutoConfigurationStore autoStoreConfigBean = context.getBean(AutoConfigurationStore.class);
    Runtime.getRuntime().addShutdownHook(new Thread(autoStoreConfigBean));
  }
}
