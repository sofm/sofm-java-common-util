package com.github.sofm.common.util.core;

public class MobileUtil {

  private MobileUtil() {
  }

  public static String formatThailandMobileNumber(String input) {
    return formatMobileNumber(input, "66");
  }

  public static String formatVietnamMobileNumber(String input) {
    return formatMobileNumber(input, "84");
  }

  public static String formatMobileNumber(String msisdn, String countryCode) {
    msisdn = removeInvalidCharacters(msisdn);
    if (msisdn.startsWith(countryCode)) {
      return msisdn;
    } else if (msisdn.startsWith("+" + countryCode)) {
      return msisdn.substring(1);
    } else {
      return msisdn.startsWith("0") ? countryCode + msisdn.substring(1) : countryCode + msisdn;
    }
  }

  public static String removeZeroAndCountryCode(String msisdn, String countryCode) {
    msisdn = removeCountryCode(msisdn, countryCode);
    msisdn = removeZeroFromStart(msisdn);
    return msisdn;
  }

  public static String removeZeroFromStart(String msisdn) {
    msisdn = removeInvalidCharacters(msisdn);
    if (msisdn.startsWith("0")) {
      msisdn = msisdn.substring(1);
    }

    return msisdn;
  }

  public static String removeCountryCode(String msisdn, String countryCode) {
    msisdn = removeInvalidCharacters(msisdn);
    if (msisdn.startsWith(countryCode)) {
      msisdn = msisdn.substring(countryCode.length());
    }

    if (msisdn.startsWith("+" + countryCode)) {
      msisdn = msisdn.substring(countryCode.length() + 1);
    }

    return msisdn;
  }

  private static String removeInvalidCharacters(String input) {
    return input.trim().replaceAll("[^\\d]", "");
  }
}
