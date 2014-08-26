package com.scancella.hermes.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.scancella.hermes.RestfulTest;
import com.scancella.hermes.domain.ConfigurationStatus;
import com.scancella.hermes.domain.StoreableConfiguration;

public class StoreableConfigurationControllerTest extends RestfulTest
{
  private StoreableConfigurationController sut;
  
  @Mock
  private StoreableConfiguration mockStoreableConfiguration;
  
  @Override
  protected Object getSut()
  {
    sut = new StoreableConfigurationController();
    sut.setConfigs(Arrays.asList(mockStoreableConfiguration));
    return sut;
  }

  @Test
  public void testStoreConfigurations() throws Exception
  {
    ConfigurationStatus status = new ConfigurationStatus();
    status.setStatusMessage("statusMessage");
    status.setStatusOk(true);
    Mockito.when(mockStoreableConfiguration.saveToConfiguration()).thenReturn(status);
    
    URI uri = new URI("/saveconfigurations.do");
    
    mockMvc.perform(get(uri))
    .andExpect(status().isOk())
    .andExpect(content().string("true"));
    
    Mockito.verify(mockStoreableConfiguration).saveToConfiguration();
  }
}
