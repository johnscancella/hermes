package com.scancella.hermes.domain;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.scancella.hermes.SimpleTest;

public class RouteTest extends SimpleTest
{
  private Route sut;
  
  @Test
  public void testPrettyPrint()
  {
    String expectedString = "Routing Path: \n" + "  ->[user1@server1]\n" + "    ->[user2@server2]\n" +
      "      ->[user2@server3]\n" + "        ->[user2@server4]\n";
    
    sut = new Route(createServerAccountLinks());
    
    assertEquals(expectedString, sut.toString());
  }
  
  @Test
  public void testOneLinePrint()
  {
    String expectedString = "Routing Path: ->[user1@server1] ->[user2@server2] ->[user2@server3] ->[user2@server4]";
      
      sut = new Route(createServerAccountLinks());
      
      assertEquals(expectedString, sut.toStringOneLine());
  }
  
  private List<ServerAccountLink> createServerAccountLinks()
  {
    Server server1 = new Server("server1", "111.111.111.111");
    Account account1 = new Account("user1", "password1");
    ServerAccountLink link1 = new ServerAccountLink(server1, account1);
    
    Server server2 = new Server("server2", "222.222.222.222");
    Account account2 = new Account("user2", "password2");
    ServerAccountLink link2 = new ServerAccountLink(server2, account2);
    
    Server server3 = new Server("server3", "333.333.333.333");
    Account account3 = new Account("user2", "password2");
    ServerAccountLink link3 = new ServerAccountLink(server3, account3);
    
    Server server4 = new Server("server4", "444.444.444.444");
    Account account4 = new Account("user2", "password2");
    ServerAccountLink link4 = new ServerAccountLink(server4, account4);
    
    return Arrays.asList(link1, link2, link3, link4);
  }
}
