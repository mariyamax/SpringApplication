package org.example.Utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class CustomTokenUtils {

    public static String encodeToToken(String username) {
        byte[] bytesEncoded = Base64.encodeBase64(username.getBytes(StandardCharsets.US_ASCII));
        return new String(bytesEncoded,StandardCharsets.US_ASCII);
    }

    public static String encodeToUsername(String token) {
        byte[] valueDecoded = Base64.decodeBase64(token.getBytes(StandardCharsets.US_ASCII));
        return new String(valueDecoded,StandardCharsets.US_ASCII);
    }
}
