package com.scancella.hermes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scancella.hermes.network.services.FileSender;
import com.scancella.hermes.network.services.NetworkRouter;
import com.scancella.hermes.rules.FileMatchingRule;
import com.scancella.hermes.rules.SendMatchingFilesJob;

/**
 * Provides a rest interface for added/modifying/deleting file regexes to send to other servers
 */
@RestController
public class FileController
{
  @Autowired
  private NetworkRouter router;
  
  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;
  
  @Autowired
  private FileSender fileSender;
  
  //every five minutes
  private static final Trigger DEFAULT_CRON_TRIGGER = new CronTrigger("*/5 * * * * *");
  
//  //DEBUG trying out running a scheduled job progammatically
//  @PostConstruct
//  public void init()
//  {
//    Trigger trigger = new CronTrigger("*/5 * * * * *");
//    
//    taskScheduler.schedule(new Runnable()
//    {      
//      @Override
//      public void run()
//      {
//        Job job = new Job();
//        job.run();
//      }
//    }, trigger);
//  }
  
  @RequestMapping("/addDirectorySearch.do")
  public String addServerAccount(@RequestParam(value="fileregex", required=true) String fileRegex, 
      @RequestParam(value="directory", required=true) String directoryToSearch,
      @RequestParam(value="server", required=true) String serverDestinationName,
      @RequestParam(value="cron", required=false) String cron) 
  {    
    FileMatchingRule rule = new FileMatchingRule(fileRegex, directoryToSearch, serverDestinationName);
    SendMatchingFilesJob job = new SendMatchingFilesJob(rule, fileSender);
    
    Trigger trigger = createTrigger(cron);
    taskScheduler.schedule(job, trigger);
    
    return "Scheduled directory search";
  }
  
  
  protected Trigger createTrigger(String cron)
  {
    if(cron != null)
    {
      return new CronTrigger(cron);
    }
    
    return DEFAULT_CRON_TRIGGER;
  }
  
}
