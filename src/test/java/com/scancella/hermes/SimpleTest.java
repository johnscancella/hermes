package com.scancella.hermes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * adds some nice methods to the basic Assert class. Does not start up spring
 * context for faster testing
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class SimpleTest extends Assert
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

  /**
   * Asserts that two Lists are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message. If
   * <code>expected</code> and <code>actual</code> are <code>null</code>,
   * they are considered equal.
   * 
   * @param expected
   *          expected value
   * @param actual
   *          actual value
   */
  static public void assertEquals(List<?> expected, List<?> actual)
  {
    assertEquals(null, expected, actual);
  }

  /**
   * Asserts that two Lists are equal. If they are not, an
   * {@link AssertionError} is thrown with the given message. If
   * <code>expected</code> and <code>actual</code> are <code>null</code>,
   * they are considered equal.
   * 
   * @param message
   *          the identifying message for the {@link AssertionError} (
   *          <code>null</code> okay)
   * @param expected
   *          expected value
   * @param actual
   *          actual value
   */
  static public void assertEquals(String message, List<?> expected, List<?> actual)
  {
    if( equalsRegardingNull(expected, actual) )
    {
      return;
    }
    else if( expected instanceof List<?> && actual instanceof List<?> )
    {
      String cleanMessage = message == null ? "" : message;
      throw new ComparisonFailure(cleanMessage, expected.toString(), actual.toString());
    }
    else if( CollectionUtils.isEqualCollection(expected, actual) )
    {
      return;
    }
    else
    {
      failNotEquals(message, expected, actual);
    }
  }

  // Code below was taken from Assert
  private static boolean equalsRegardingNull(Object expected, Object actual)
  {
    if( expected == null )
    {
      return actual == null;
    }
    return isEquals(expected, actual);
  }

  private static boolean isEquals(Object expected, Object actual)
  {
    return expected.equals(actual);
  }

  static private void failNotEquals(String message, Object expected, Object actual)
  {
    fail(format(message, expected, actual));
  }

  static String format(String message, Object expected, Object actual)
  {
    String formatted = "";
    if( message != null && !message.equals("") )
    {
      formatted = message + " ";
    }
    String expectedString = String.valueOf(expected);
    String actualString = String.valueOf(actual);
    if( expectedString.equals(actualString) )
    {
      return formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString);
    }
    else
    {
      return formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
    }
  }

  private static String formatClassAndValue(Object value, String valueString)
  {
    String className = value == null ? "null" : value.getClass().getName();
    return className + "<" + valueString + ">";
  }
}
