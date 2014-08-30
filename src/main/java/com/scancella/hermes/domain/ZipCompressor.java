package com.scancella.hermes.domain;

import java.io.File;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;

public class ZipCompressor extends LoggingObject implements FileCompressor
{
//  @Value("${file.compressor.temp.dir:/tmp/fileCompressionDir}")
  @Value("${file.compressor.temp.dir:/tmp/fileCompressionDir}")
  private File tempFileDirectory;

  @Override
  public File compressFile(File file)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public File compressFiles(Collection<File> file)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public File getTempFileDirectory()
  {
    return tempFileDirectory;
  }

  public void setTempFileDirectory(File tempFileDirectory)
  {
    this.tempFileDirectory = tempFileDirectory;
  }
  
}
