package org.example.Utils;

import org.apache.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;

public class CustomTokenUtils {

    public static String encodeToToken(String username, String password) {
        String credential = username+","+password;
        byte[] bytesEncoded = Base64.encodeBase64(credential.getBytes(StandardCharsets.US_ASCII));
        return new String(bytesEncoded,StandardCharsets.US_ASCII);
    }

    public static String decodeToUsername(String token) {
        byte[] valueDecoded = Base64.decodeBase64(token.getBytes(StandardCharsets.US_ASCII));
        String credential = new String(valueDecoded,StandardCharsets.US_ASCII);
        return credential.substring(0, credential.indexOf(','));
    }

    public static String decodeToPassword(String token) {
        byte[] valueDecoded = Base64.decodeBase64(token.getBytes(StandardCharsets.US_ASCII));
        String credential = new String(valueDecoded,StandardCharsets.US_ASCII);
        return credential.substring(credential.indexOf(',')+1);
    }

}
