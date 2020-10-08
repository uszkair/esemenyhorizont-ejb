package hu.aeek.esemenyhorizont.security.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordService {
   public PasswordService() {
   }

   public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
      byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
      return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
   }

   public static boolean authenticate(String attemptedPassword, String encryptedPasswordString, String saltString) throws NoSuchAlgorithmException, InvalidKeySpecException {
      return authenticate(attemptedPassword, Base64.getDecoder().decode(encryptedPasswordString), Base64.getDecoder().decode(saltString));
   }

   public static byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
      String algorithm = "PBKDF2WithHmacSHA1";
      int derivedKeyLength = 160;
      int iterations = 20000;
      KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
      SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
      return f.generateSecret(spec).getEncoded();
   }

   public static String getEncryptedPasswordString(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
      return Base64.getEncoder().encodeToString(getEncryptedPassword(password, Base64.getDecoder().decode(salt)));
   }

   public static byte[] generateSalt() throws NoSuchAlgorithmException {
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      byte[] salt = new byte[8];
      random.nextBytes(salt);
      return salt;
   }

   public static String generateSaltString() throws NoSuchAlgorithmException {
      return Base64.getEncoder().encodeToString(generateSalt());
   }
}
