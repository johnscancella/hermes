package com.scancella.hermes.network.services;

import java.io.File;
import java.util.Collection;

import com.scancella.hermes.network.domain.FileSendingResponse;

public interface FileSender
{
  /**
   * returns true if was able to send all the files.
   */
  public FileSendingResponse sendFiles(Collection<File> files);
}
