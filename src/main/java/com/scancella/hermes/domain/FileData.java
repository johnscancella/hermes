package com.scancella.hermes.domain;

public class FileData
{
  private final byte[] fileData;
  private final String filename;
  
  public FileData(byte[] fileData, String filename)
  {
    this.fileData = fileData;
    this.filename = filename;
  }

  public byte[] getFileData()
  {
    return fileData;
  }

  public String getFilename()
  {
    return filename;
  }
}
