package com.scancella.hermes.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scancella.hermes.domain.ConfigurationStatus;
import com.scancella.hermes.domain.JobTriggerInfo;
import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.domain.StoreableConfiguration;
import com.scancella.hermes.network.services.FileSender;
import com.scancella.hermes.network.services.NetworkRouter;
import com.scancella.hermes.rules.FileMatchingRule;
import com.scancella.hermes.rules.SendMatchingFilesJob;

/**
 * Provides a rest interface for added/modifying/deleting file regexes to send to other servers
 */
@RestController
public class FileController extends LoggingObject implements StoreableConfiguration
{
  @Autowired
  private NetworkRouter router;
  
  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;
  
  @Autowired
  private FileSender fileSender;
  
  private Set<JobTriggerInfo> jobsAndTriggers;
  
  //every five minutes
  private static final CronTrigger DEFAULT_CRON_TRIGGER = new CronTrigger("*/5 * * * * *");
  
  private static final Resource regexesConfigResource = new PathResource("regexes.json");
  
  @PostConstruct
  public void init()
  {
    jobsAndTriggers = new HashSet<>();
    restoreConfiguration();
  }
  
  @Override
  public ConfigurationStatus saveToConfiguration()
  {
    logger.debug("Called save configuration!");
    ConfigurationStatus status = new ConfigurationStatus();
    
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(regexesConfigResource.getFile(), jobsAndTriggers);
      
      status.setStatusOk(true);
      status.setStatusMessage("Successfully saved Jobs and Triggers to " + regexesConfigResource.getFilename());
    }
    catch(Exception e)
    {
      logger.error("Error marshalling obs and Triggers set", e);
      status.setStatusOk(false);
      status.setStatusMessage("Failed to save obs and Triggers to " + regexesConfigResource.getFilename());
    }
    
    return status;
  }


  @Override
  public ConfigurationStatus restoreConfiguration()
  {
    logger.debug("Called restore configuration!");
    ConfigurationStatus status = new ConfigurationStatus();
    
    if(regexesConfigResource.exists())
    {
      status = restoreConfigurationFromResource();
    }
    else
    {
      status.setStatusMessage("No configuration to restore from");
      status.setStatusOk(true);
    }
    
    return status;
  }
  
  protected ConfigurationStatus restoreConfigurationFromResource()
  {
    ConfigurationStatus status = new ConfigurationStatus();
    
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      Set<JobTriggerInfo> info = mapper.readValue(regexesConfigResource.getInputStream(), new TypeReference<Set<JobTriggerInfo>>(){});
      jobsAndTriggers.addAll(info);
      addJobsAndTriggersToScheduler(info);
      
      status.setStatusOk(true);
      status.setStatusMessage("Successfully restored Jobs and Triggers from " + regexesConfigResource.getFilename());
    }
    catch(Exception e)
    {
      logger.error("Error unmarshalling Jobs and Triggers set", e);
      status.setStatusOk(false);
      status.setStatusMessage("Failed to restore Jobs and Triggers from " + regexesConfigResource.getFilename());
    }
    
    return status;
  }


  protected void addJobsAndTriggersToScheduler(Set<JobTriggerInfo> infos)
  { 
    for(JobTriggerInfo info : infos)
    {
      FileMatchingRule rule = new FileMatchingRule(info.getFileMatchingRegex(), info.getScanDirectory(), info.getDestinationServer());
      SendMatchingFilesJob job = new SendMatchingFilesJob(rule, fileSender);
      CronTrigger trigger = createTrigger(info.getCronTriggerExpression());
      
      taskScheduler.schedule(job, trigger);
    }
  }

  @Override
  public Resource getConfigurationResource()
  {
    return regexesConfigResource;
  }
  
  @RequestMapping("/addDirectorySearch.do")
  public String addServerAccount(@RequestParam(value="fileregex", required=true) String fileRegex, 
      @RequestParam(value="directory", required=true) String directoryToSearch,
      @RequestParam(value="server", required=true) String serverDestinationName,
      @RequestParam(value="cron", required=false) String cron) 
  {    
    FileMatchingRule rule = new FileMatchingRule(fileRegex, directoryToSearch, serverDestinationName);
    SendMatchingFilesJob job = new SendMatchingFilesJob(rule, fileSender);
    
    CronTrigger trigger = createTrigger(cron);
    taskScheduler.schedule(job, trigger);
    
    addJobAndTriggerToSet(job, trigger);
    
    return "Scheduled directory search";
  }
  
  
  protected void addJobAndTriggerToSet(SendMatchingFilesJob job, CronTrigger trigger)
  {
    JobTriggerInfo info = new JobTriggerInfo();
    
    info.setCronTriggerExpression(trigger.getExpression());
    info.setDestinationServer(job.getRule().getDestinationServer());
    info.setFileMatchingRegex(job.getRule().getRegexExpression());
    info.setScanDirectory(job.getRule().getScanDirectory());
    
    jobsAndTriggers.add(info);
  }

  protected CronTrigger createTrigger(String cron)
  {
    if(cron != null)
    {
      return new CronTrigger(cron);
    }
    
    return DEFAULT_CRON_TRIGGER;
  }

  public Set<JobTriggerInfo> getJobsAndTriggers()
  {
    return jobsAndTriggers;
  }

  public NetworkRouter getRouter()
  {
    return router;
  }

  public void setRouter(NetworkRouter router)
  {
    this.router = router;
  }

  public ThreadPoolTaskScheduler getTaskScheduler()
  {
    return taskScheduler;
  }

  public void setTaskScheduler(ThreadPoolTaskScheduler taskScheduler)
  {
    this.taskScheduler = taskScheduler;
  }

  public FileSender getFileSender()
  {
    return fileSender;
  }

  public void setFileSender(FileSender fileSender)
  {
    this.fileSender = fileSender;
  }
  
}
