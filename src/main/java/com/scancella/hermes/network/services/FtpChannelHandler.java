package com.scancella.hermes.network.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.messaging.MessageChannel;

import com.scancella.hermes.network.domain.Server;

/**
 * Handles creating and managing spring contexts for each server. Based off the example of the dynamic ftp from spring integration examples
 */
public class FtpChannelHandler
{
  private final LinkedHashMap<String, MessageChannel> channels = new LinkedHashMap<String, MessageChannel>();
  private final Map<MessageChannel, ConfigurableApplicationContext> contexts = new HashMap<MessageChannel, ConfigurableApplicationContext>();
  private static final String[] XML_CONFIGs = new String[] {"/META-INF/spring/integration/dynamic-ftp-outbound-adapter-context.xml"}; //TODO replace with my own context template
  
  public MessageChannel findChannel(Server server)
  {
    return this.channels.get(server.getIpVersion4());
  }
  
  public synchronized MessageChannel createChannel(Server server, String destinationDir)
  {
    MessageChannel channel = channels.get(server.getIpVersion4());
    if (channel == null) 
    {
      ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(XML_CONFIGs, false);
      setFtpSettings(ctx, server, destinationDir);
      
      channel = ctx.getBean("toFtpChannel", MessageChannel.class);
      channels.put(server.getIpVersion4(), channel);
      contexts.put(channel, ctx);
    }
    
    return channel;
  }
  
  private ConfigurableApplicationContext setFtpSettings(ConfigurableApplicationContext ctx, Server server, String destinationDir) {
    StandardEnvironment env = new StandardEnvironment();
    
    Properties props = new Properties();
    props.setProperty("host", server.getIpVersion4());
    props.setProperty("user", server.getAccount().getUsername());
    props.setProperty("password", server.getAccount().getPassword());
    props.setProperty("remote.directory", destinationDir);
    PropertiesPropertySource pps = new PropertiesPropertySource("serverFtpProperties", props);
    
    env.getPropertySources().addLast(pps);
    ctx.setEnvironment(env);
    ctx.refresh();
    
    return ctx;
  }
}
