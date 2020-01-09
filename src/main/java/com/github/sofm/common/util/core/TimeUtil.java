package com.github.sofm.common.util.core;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class TimeUtil {

  private TimeUtil() {
  }

  public static String formatTimeDiff(long finishTime, long startTime) {
    long timeDiff = finishTime - startTime;
    return formatTime(timeDiff);
  }

  public static String formatTime(long time) {
    StringBuffer stringBuffer = new StringBuffer();
    long hours = time / 3600000L;
    long rem = time % 3600000L;
    long minutes = rem / 60000L;
    rem %= 60000L;
    long seconds = rem / 1000L;
    if (hours != 0L) {
      stringBuffer.append(hours);
      stringBuffer.append("hour, ");
    }

    if (minutes != 0L) {
      stringBuffer.append(minutes);
      stringBuffer.append("min, ");
    }

    stringBuffer.append(seconds);
    stringBuffer.append("sec");
    return stringBuffer.toString();
  }

  public static String getFormattedTimeWithDiff(DateFormat dateFormat, long finishTime, long startTime) {
    StringBuffer stringBuffer = new StringBuffer();
    if (finishTime != 0L) {
      stringBuffer.append(dateFormat.format(new Date(finishTime)));
      if (startTime != 0L) {
        stringBuffer.append(" (" + formatTimeDiff(finishTime, startTime) + ")");
      }
    }

    return stringBuffer.toString();
  }

  public static long getCurrentTime() {
    Instant instant = Instant.now();
    return instant.getEpochSecond() * 1000;
  }
}
