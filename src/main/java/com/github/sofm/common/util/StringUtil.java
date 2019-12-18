package com.github.sofm.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class StringUtil {

  public static String toNative(String input) {
    return "\"" + input + "\"";
  }

  public static String toString(InputStream inputStream) throws IOException {
    StringBuilder textBuilder = new StringBuilder();

    try (Reader reader =
        new BufferedReader(
            new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
      int c;
      while ((c = reader.read()) != -1) {
        textBuilder.append((char) c);
      }
    }

    return textBuilder.toString();
  }

  public static long getLong(String input, Long defaultValue) {
    try {
      return Long.parseLong(input);
    } catch (Exception ex) {
      log.info("(getLong)PARSE_LONG_ERROR, input: {}, defaultValue: {}", input, defaultValue);
      return defaultValue;
    }
  }

  public static Integer getInteger(String input, Integer defaultValue) {
    try {
      return Integer.parseInt(input);
    } catch (Exception ex) {
      log.info("(getInteger)PARSE_INTEGER_ERROR, input: {}, defaultValue: {}", input, defaultValue);
      return defaultValue;
    }
  }

  public static Boolean getBoolean(String input, Boolean defaultValue) {
    try {
      return Boolean.parseBoolean(input);
    } catch (Exception ex) {
      log.info("(getBoolean)PARSE_BOOLEAN_ERROR, input: {}, defaultValue: {}", input, defaultValue);
      return defaultValue;
    }
  }

  public static boolean isValid(String input) {
    if (input == null) {
      return false;
    } else {
      return !input.trim().isEmpty();
    }
  }

  public static String getParentFolder(String folderPath) {
    int lastIndex = folderPath.lastIndexOf(47);
    return folderPath.substring(0, lastIndex + 1);
  }

  public static String toUrl(String input) {
    try {
      String temp = Normalizer.normalize(input, Form.NFD);
      Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
      return pattern.matcher(temp).replaceAll("").toLowerCase().replace(" ", "-").replace("đ", "d");
    } catch (Exception ex) {
      log.error("(toUrl)ex: {}", ExceptionUtil.getFullStackTrace(ex));
      return null;
    }
  }
}
