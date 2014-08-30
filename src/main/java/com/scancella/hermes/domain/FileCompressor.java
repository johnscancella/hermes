package com.scancella.hermes.domain;

import java.io.File;
import java.util.Collection;

public interface FileCompressor
{
  File compressFile(File file);
  
  File compressFiles(Collection<File> file);
}
