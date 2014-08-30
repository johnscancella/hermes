package com.scancella.hermes.domain;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scancella.hermes.SimpleTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/*-spring.xml" })
public class ZipCompressorTest extends SimpleTest
{
  @Autowired
  private ZipCompressor sut;
  
  private Resource testFileToCompress = new ClassPathResource("META-INF/data/testFileToCompress.txt");
  
  @Test
  public void test() throws IOException
  {
    File compressesFile = sut.compressFile(testFileToCompress.getFile());
    assertTrue(compressesFile.length() < testFileToCompress.getFile().length());
    System.err.println("hello!");
  }
}
