package com.github.sofm.common.util.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

@Slf4j
public class StringUtil {

  private StringUtil() {
  }

  public static boolean isValid(String input) {
    if (input == null) {
      return false;
    }

    return !input.trim().isEmpty();
  }

  public static boolean isHtml(final String htmlString) {
    if (!htmlString.contains("<") || !htmlString.contains(">")) {
      return false;
    }

    try {
      Jsoup.parse(htmlString);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  public static boolean isJson(String input) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(input);
      return true;
    } catch (IOException ex) {
      return false;
    }
  }

  public static boolean equalsAny(String input, String... matchesAny) {
    return equalsAny(input, Arrays.asList(matchesAny));
  }

  public static boolean equalsAny(String input, List<String> matchesAny) {
    return matchesAny != null && matchesAny.contains(input);
  }

  public static boolean startsWithAny(String input, String... startsWithAny) {
    return Arrays.stream(startsWithAny).findFirst().filter(input::startsWith).isPresent();
  }

  public static String toNative(String input) {
    return "\"" + input + "\"";
  }

  public static String toString(InputStream inputStream) throws IOException {
    StringBuilder textBuilder = new StringBuilder();

    try (Reader reader = new BufferedReader(
        new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
      int c;
      while ((c = reader.read()) != -1) {
        textBuilder.append((char) c);
      }
    }

    return textBuilder.toString();
  }

  public static String toFriendlyUrl(String input) {
    try {
      String temp = Normalizer.normalize(input, Form.NFD);
      Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
      return pattern.matcher(temp).replaceAll("").toLowerCase().replace(" ", "-").replace("đ", "d");
    } catch (Exception ex) {
      return null;
    }
  }

  public static String removeAccent(String s) {
    StringBuilder sb = new StringBuilder(s);
    for (int i = 0; i < sb.length(); i++) {
      sb.setCharAt(i, removeAccent(sb.charAt(i)));
    }

    return sb.toString();
  }

  private static char removeAccent(char ch) {
    String special = "àÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬđĐèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆìÌỉỈĩĨíÍịỊòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰýÝỹỸỳỲỷỶỵỴ :+\\<>\"*,!?%$=@#~[]`|^'.;＆&";
    String replace = "aAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAdDeEeEeEeEeEeEeEeEeEeEeEiIiIiIiIiIoOoOoOoOoOoOoOoOoOoOoOoOoOoOoOoOoOuUuUuUuUuUuUuUuUuUuUuUyYyYyYyYyY----\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0----";
    int index = special.indexOf(ch);
    if (index >= 0) {
      ch = replace.charAt(index);
    }

    return ch;
  }
}
