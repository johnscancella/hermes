package com.scancella.hermes.network.services;

import java.io.File;
import java.util.Collection;

import com.scancella.hermes.core.LoggingObject;
import com.scancella.hermes.network.domain.FileSendingResponse;

/**
 * Does nothing but log which files would have been sent. Useful for debugging.
 */
public class DoNothingFileSender extends LoggingObject implements FileSender
{
  @Override
  public FileSendingResponse sendFiles(Collection<File> files)
  {
    for(File file : files)
    {
      logger.info("Would have sent file: " + file.getAbsolutePath() + File.separator + file.getName());
    }
    
    return new FileSendingResponse(true, "Used the 'DoNothingFileSender'");
  }

}
