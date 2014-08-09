package com.scancella.hermes.rules;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.network.domain.FileSendingResponse;
import com.scancella.hermes.network.services.FileSender;

public class SendMatchingFilesJob extends LoggingObject implements Runnable
{
  private final FileMatchingRule rule;
  private final FileSender sender;
  
  public SendMatchingFilesJob(FileMatchingRule rule, FileSender sender)
  {
    this.rule = rule;
    this.sender = sender;
  }
  
  @Override
  public void run()
  {
    Resource scanDirectory = new FileSystemResource(rule.getScanDirectory());
    IOFileFilter filter = new RegexFileFilter(rule.getRegexExpression());
    try
    {
      @SuppressWarnings("unchecked")
      Collection<File> files = FileUtils.listFiles(scanDirectory.getFile(), filter , FileFilterUtils.trueFileFilter());
      FileSendingResponse response = sender.sendFiles(files, rule.getDestinationServer());
      
      String message = (response.isSuccessful()? "Successfully" : "Unsuccessfully") + " send file!";
      logger.info(message);
      
      logger.debug("Response message was: " + response.getMessage());
    }
    catch(IOException e)
    {
      throw new RuntimeException("Error getting files to send!", e);
    }
  }

  public FileMatchingRule getRule()
  {
    return rule;
  }

  public FileSender getSender()
  {
    return sender;
  }

}
