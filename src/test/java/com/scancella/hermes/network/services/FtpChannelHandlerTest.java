package com.scancella.hermes.network.services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.messaging.MessageChannel;

import com.scancella.hermes.SimpleTest;
import com.scancella.hermes.network.domain.Account;
import com.scancella.hermes.network.domain.Server;

public class FtpChannelHandlerTest extends SimpleTest
{
  private FtpChannelHandler sut;
  private Account account;
  private Server server;
  
  @Before
  public void setup()
  {
    sut = new FtpChannelHandler();
    
    account = new Account("username", "password");
    server = new Server("serverName", "ipVersion4");
    server.setAccount(account);
  }
  
  @Test
  public void testEnsurePropertiesAreSetCorrectlyInSpringContext()
  {
    String destinationDir = "/tmp/foo";
    
    ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(FtpChannelHandler.getXmlConfigs(), false);
    ConfigurableApplicationContext setContext = sut.setFtpSettings(context, server, destinationDir);
    
    ConfigurableEnvironment env = setContext.getEnvironment();
    assertEquals(server.getIpVersion4(), env.getProperty("host"));
    assertEquals(server.getAccount().getUsername(), env.getProperty("user"));
    assertEquals(server.getAccount().getPassword(), env.getProperty("password"));
    assertEquals(destinationDir, env.getProperty("remote.directory"));
  }
  
  @Test
  public void testCreateChannel()
  {
    String destinationDir = "/tmp/foo";
    
    MessageChannel channel = sut.createChannel(server, destinationDir);
    assertNotNull(channel);
    assertEquals(1, sut.getChannels().size());
    assertEquals(1, sut.getContexts().size());
  }
}
