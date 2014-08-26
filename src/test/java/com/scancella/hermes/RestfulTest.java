package com.scancella.hermes;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class RestfulTest extends SimpleTest
{
  protected MockMvc mockMvc;
  
  @Before
  public void setup()
  {
    mockMvc = MockMvcBuilders.standaloneSetup(getSut()).build();
  }
  
  protected abstract Object getSut();
}
