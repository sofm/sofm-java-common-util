package com.github.sofm.common.util.core;

import java.text.DecimalFormat;

public class CapacityUtil {

  private CapacityUtil() {
  }

  public static String formatCapacity(long capacity) {
    DecimalFormat oneDecimal = new DecimalFormat("0.0");
    long absNumber = Math.abs(capacity);
    double result = capacity;
    String suffix = " B";
    if (absNumber < 1024) {
      // nothing
    } else if (absNumber < 1024 * 1024) {
      result = capacity / 1024.0;
      suffix = " KB";
    } else if (absNumber < 1024 * 1024 * 1024) {
      result = capacity / (1024.0 * 1024);
      suffix = " MB";
    } else {
      result = capacity / (1024.0 * 1024 * 1024);
      suffix = " GB";
    }

    return oneDecimal.format(result) + suffix;
  }

  public static String byteToHex(byte[] bytes) {
    return byteToHex(bytes, 0, bytes.length);
  }

  public static String byteToHex(byte[] bytes, int start, int end) {
    if (bytes == null) {
      throw new IllegalArgumentException("bytes == null");
    }

    StringBuilder s = new StringBuilder();
    for (int i = start; i < end; i++) {
      s.append(String.format("%02x", bytes[i]));
    }

    return s.toString();
  }

  public static byte[] hexToByte(String hex) {
    byte[] bts = new byte[hex.length() / 2];
    for (int i = 0; i < bts.length; i++) {
      bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }

    return bts;
  }
}
