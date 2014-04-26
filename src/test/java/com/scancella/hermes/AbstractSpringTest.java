package com.scancella.hermes;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ComponentScan({ "com.scancella.hermes.services", "com.scancella.hermes.controllers", "com.scancella.hermes.mappers" })
@EnableAutoConfiguration
@ImportResource({ "classpath:META-INF/*-spring.xml" })
public abstract class AbstractSpringTest extends SimpleTest
{
}
