package com.github.sofm.common.util.core;

public class SystemUtil {

  private static final String OS = System.getProperty("os.name").toLowerCase();

  private SystemUtil() {
  }

  public static boolean isWindows() {
    return OS.contains("win");
  }

  public static boolean isMac() {
    return OS.contains("mac");
  }

  public static boolean isUnix() {
    return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
  }

  public static boolean isSolaris() {
    return OS.contains("sunos");
  }

  public static String getOS() {
    if (isWindows()) {
      return "win";
    } else if (isMac()) {
      return "osx";
    } else if (isUnix()) {
      return "uni";
    } else {
      return isSolaris() ? "sol" : "err";
    }
  }
}
