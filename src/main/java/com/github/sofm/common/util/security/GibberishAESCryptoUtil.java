package com.github.sofm.common.util.security;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.lang.Validate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class GibberishAESCryptoUtil {

  private static final String CIPHER_ALG = "PBEWITHMD5AND256BITAES-CBC-OPENSSL";

  private static final Provider CIPHER_PROVIDER = new BouncyCastleProvider();

  private static final String PREFIX = "Salted__";

  private Random rand = new Random();

  private GibberishAESCryptoUtil() {
  }

  public String encrypt(String plainText, char[] password) throws Exception {
    byte[] salt = new byte[8];
    rand.nextBytes(salt);
    Cipher cipher = createCipher(Cipher.ENCRYPT_MODE, salt, password);
    byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    ByteArrayOutputStream baos = new ByteArrayOutputStream(cipherText.length + 16);
    baos.write(PREFIX.getBytes(StandardCharsets.UTF_8));
    baos.write(salt);
    baos.write(cipherText);
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }

  public String decrypt(String cipherText, char[] password) throws Exception {
    byte[] input = Base64.getDecoder().decode(cipherText);
    String prefixText = new String(input, 0, 8, StandardCharsets.UTF_8);
    Validate.isTrue(prefixText.equals(PREFIX), "Invalid prefix: ", prefixText);
    byte[] salt = new byte[8];
    System.arraycopy(input, 8, salt, 0, salt.length);
    Cipher cipher = createCipher(Cipher.DECRYPT_MODE, salt, password);
    byte[] plainText = cipher.doFinal(input, 16, input.length - 16);
    return new String(plainText, StandardCharsets.UTF_8);
  }

  private Cipher createCipher(int cipherMode, byte[] salt, char[] password) throws Exception {
    PBEKeySpec pbeSpec = new PBEKeySpec(password);
    SecretKeyFactory keyFact = SecretKeyFactory.getInstance(CIPHER_ALG, CIPHER_PROVIDER);
    PBEParameterSpec defParams = new PBEParameterSpec(salt, 0);
    Cipher cipher = Cipher.getInstance(CIPHER_ALG, CIPHER_PROVIDER);
    cipher.init(cipherMode, keyFact.generateSecret(pbeSpec), defParams);
    return cipher;
  }
}
