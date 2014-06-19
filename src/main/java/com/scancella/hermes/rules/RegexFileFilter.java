package com.scancella.hermes.rules;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.IOFileFilter;

public class RegexFileFilter implements IOFileFilter
{
  private final Pattern pattern;
  
  public RegexFileFilter(String regex)
  {
    pattern = Pattern.compile(regex);
  }
  
  @Override
  public boolean accept(File pathname)
  {
    Matcher matcher = pattern.matcher(pathname.getName());
    return matcher.matches();
  }

  @Override
  public boolean accept(File dir, String name)
  {
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }

}
