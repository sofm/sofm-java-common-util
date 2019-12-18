package com.github.sofm.common.util.security;

import java.nio.charset.StandardCharsets;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

@NoArgsConstructor
public class AuthorizationUtil {

  public static String generateBasicAuthorization(String username, String password) {
    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
    return "Basic " + new String(encodedAuth);
  }
}
