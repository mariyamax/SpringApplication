package org.example.Utils;

import lombok.SneakyThrows;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SecurityUtils {

    private static final String TOKEN_KEY = "SOME__TOKEN__KEY";

    private static SecretKey key = new SecretKeySpec(TOKEN_KEY.getBytes(),"AES");

    @SneakyThrows
    public static String encode(String s) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] bytesEncoded = cipher.doFinal(s.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytesEncoded);
    }

    @SneakyThrows
    public static String decode(String s) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] bytesDecoded = cipher.doFinal( Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)));
        return new String(bytesDecoded,StandardCharsets.UTF_8);
    }

}
