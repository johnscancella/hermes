package com.scancella.hermes.controllers;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.scancella.hermes.SimpleTest;
import com.scancella.hermes.core.JobTriggerInfo;
import com.scancella.hermes.network.services.FileSender;
import com.scancella.hermes.network.services.NetworkRouter;

public class FileControllerTest extends SimpleTest
{
  private FileController sut;
  
  private FileSender mockSender;
  private NetworkRouter mockRouter;
  private ThreadPoolTaskScheduler mockTaskScheduler;
  
  @Before
  public void setup()
  {
    sut = new FileController();
    
    mockSender = Mockito.mock(FileSender.class);
    mockRouter = Mockito.mock(NetworkRouter.class);
    mockTaskScheduler = Mockito.mock(ThreadPoolTaskScheduler.class);
    sut.setFileSender(mockSender);
    sut.setRouter(mockRouter);
    sut.setTaskScheduler(mockTaskScheduler);
    
    sut.init();
  }
  
  @After
  public void teardown() throws IOException
  {
    if(sut.getConfigurationResource().exists())
    {
      sut.getConfigurationResource().getFile().delete();
    }
  }
  
  @Test
  public void testSaveJobTriggers()
  {
    saveJobTriggers();
    assertTrue(sut.getConfigurationResource().exists());
  }
  
  private void saveJobTriggers()
  {
    JobTriggerInfo jobTrigger1 = new JobTriggerInfo();
    jobTrigger1.setCronTriggerExpression("*/5 * * * * *");
    jobTrigger1.setDestinationServer("destinationServer1");
    jobTrigger1.setFileMatchingRegex("fileMatchingRegex1");
    jobTrigger1.setScanDirectory("scanDirectory1");
    sut.getJobsAndTriggers().add(jobTrigger1);
    
    JobTriggerInfo jobTrigger2 = new JobTriggerInfo();
    jobTrigger2.setCronTriggerExpression("*/10 * * * * *");
    jobTrigger2.setDestinationServer("destinationServer2");
    jobTrigger2.setFileMatchingRegex("fileMatchingRegex2");
    jobTrigger2.setScanDirectory("scanDirectory2");
    sut.getJobsAndTriggers().add(jobTrigger2);
    
    sut.saveToConfiguration();
  }
  
  @Test
  public void testRestoreJobTriggers()
  {
    saveJobTriggers();
    
    sut = new FileController();
    sut.setFileSender(mockSender);
    sut.setRouter(mockRouter);
    sut.setTaskScheduler(mockTaskScheduler);
    sut.init();
    
    assertEquals(2, sut.getJobsAndTriggers().size());
  }
}
