package com.github.sofm.common.util.security;

import com.github.sofm.common.util.ExceptionUtil;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class HashUtil {

  public static String hmacSHA256(String key, String input) {
    log.debug("(hmacSHA256)key: {}, input: {}", key, input);

    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
      byte[] result = mac.doFinal(input.getBytes());
      return DatatypeConverter.printHexBinary(result).toLowerCase();
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      log.error("(hmacSHA256)ex: {}", ExceptionUtil.getFullStackTrace(e));
      return null;
    }
  }
}
