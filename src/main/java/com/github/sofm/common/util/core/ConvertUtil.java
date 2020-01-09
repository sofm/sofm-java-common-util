package com.github.sofm.common.util.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertUtil {

  private ConvertUtil() {
  }

  public static Long getLong(String input, Long defaultValue) {
    try {
      return Long.parseLong(input);
    } catch (Exception ex) {
      log.info("(getLong) PARSE_LONG_ERROR, input: {}, defaultValue: {}", input, defaultValue);
      return defaultValue;
    }
  }

  public static Integer getInteger(String input, Integer defaultValue) {
    try {
      return Integer.parseInt(input);
    } catch (Exception ex) {
      log.info("(getInteger) PARSE_INTEGER_ERROR, input: {}, defaultValue: {}", input,
          defaultValue);
      return defaultValue;
    }
  }

  public static Boolean getBoolean(String input, Boolean defaultValue) {
    try {
      return Boolean.parseBoolean(input);
    } catch (Exception ex) {
      log.info("(getBoolean) PARSE_BOOLEAN_ERROR, input: {}, defaultValue: {}", input,
          defaultValue);
      return defaultValue;
    }
  }

  public static int toInt(boolean input) {
    return (input) ? 1 : 0;
  }
}
