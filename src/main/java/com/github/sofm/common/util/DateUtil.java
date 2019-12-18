package com.github.sofm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class DateUtil {

  private static final String DEFAULT_SIMPLE_DATE_FORMAT = "yyyyMMdd";

  public static Date toDate(String date) {
    return toDate(date, DEFAULT_SIMPLE_DATE_FORMAT);
  }

  public static Date toDate(String date, String simpleDateFormat) {
    SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);
    return toDate(date, format);
  }

  public static Date toDate(String date, SimpleDateFormat simpleDateFormat) {
    try {
      return simpleDateFormat.parse(date);
    } catch (NullPointerException | ParseException ex) {
      log.info("(toDate)PARSE_ERROR, date:{}, pattern: {}", date, simpleDateFormat.toPattern());
      return null;
    }
  }

  public static String toString(Date date) {
    return toString(date, DEFAULT_SIMPLE_DATE_FORMAT);
  }

  public static String toString(Date date, String simpleDateFormat) {
    SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);
    return toString(date, format);
  }

  public static String toString(Date date, SimpleDateFormat simpleDateFormat) {
    return date == null ? null : simpleDateFormat.format(date);
  }

  public static String toString(Date from, Date until, String simpleDateFormat) {
    return "{'since':'"
        + toString(from, simpleDateFormat)
        + "','until':'"
        + toString(until, simpleDateFormat)
        + "'}";
  }

  public static Date change(Date date, boolean increase) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    if (increase) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    } else {
      calendar.add(Calendar.DAY_OF_MONTH, -1);
    }

    return calendar.getTime();
  }

  public static List<String> toList(Date from, Date until, String simpleDateFormat) {
    List<String> list = new ArrayList<>();

    for (Date tmpDate = from; tmpDate.before(until); tmpDate = change(tmpDate, true)) {
      list.add(toString(tmpDate, simpleDateFormat));
    }

    return list;
  }

  public static Long toMilliseconds(String date) {
    return toMilliseconds(date, DEFAULT_SIMPLE_DATE_FORMAT);
  }

  public static Long toMilliseconds(String date, long defaultValue) {
    return toMilliseconds(date, DEFAULT_SIMPLE_DATE_FORMAT, defaultValue);
  }

  public static Long toMilliseconds(String date, String simpleDateFormat, long defaultValue) {
    Long result = toMilliseconds(date, simpleDateFormat);
    return result == null ? defaultValue : result;
  }

  public static Long toMilliseconds(String date, String simpleDateFormat) {
    Date convertedDate = toDate(date, simpleDateFormat);
    return convertedDate == null ? null : convertedDate.getTime();
  }
}
