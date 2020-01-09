package com.github.sofm.common.util.core;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

  private ExceptionUtil() {
  }

  public static String getFullStackTrace(Exception e) {
    return "(getFullStackTrace) " + getFullStackTraceRaw(e);
  }

  public static String getFullStackTrace(Exception e, boolean isRaw) {
    return isRaw
        ? getFullStackTraceRaw(e)
        : "(getFullStackTrace) " + getFullStackTraceRaw(e).replaceAll("\n", "[NEW_LINE]");
  }

  public static String getFullStackTraceRaw(Exception e) {
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter(sWriter);
    e.printStackTrace(pWriter);
    return sWriter.toString();
  }
}
