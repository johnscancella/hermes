package com.scancella.hermes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;

/**
 * adds some nice methods to the basic Assert class. Does not start up spring context for faster testing
 */
public class SimpleTest extends Assert
{
  private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
  
  /**
   * throws the parseException as a runtime exception
   */
  public Date parseDate(String date)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
    try
    {
      return sdf.parse(date);
    }
    catch(ParseException e)
    {
      throw new RuntimeException(e);
    }
  }
}
