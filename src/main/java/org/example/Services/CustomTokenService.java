package org.example.Services;

import org.apache.commons.codec.binary.Base64;
import org.example.Models.RefreshTokens;
import org.example.Repositories.TokensRepository;
import org.example.Utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomTokenService {

    @Autowired
    private TokensRepository tokensRepository;

    public RefreshTokens encodeToRefreshToken(String username, String password, String mail) {
        String credential = username+","+password+":"+mail;
        String value = SecurityUtils.encode(credential);
        RefreshTokens refreshTokens = new RefreshTokens();
        refreshTokens.setValue(value);
        return tokensRepository.save(refreshTokens);
    }

    public String decodeToUsername(RefreshTokens token) {
        String credentials = SecurityUtils.decode(token.getValue());
        return credentials.substring(0, credentials.indexOf(','));
    }

    public String decodeToUsername(String accessToken) {
        RefreshTokens token = getRefreshToken(accessToken);
        String credentials = SecurityUtils.decode(token.getValue());
        return credentials.substring(0, credentials.indexOf(','));
    }

    public String decodeToMail(RefreshTokens token) {
        String credentials = SecurityUtils.decode(token.getValue());
        return credentials.substring(credentials.indexOf(':')+1);
    }

    public String decodeToMail(String accessToken) {
        RefreshTokens token = getRefreshToken(accessToken);
        String credentials = SecurityUtils.decode(token.getValue());
        return credentials.substring(credentials.indexOf(':')+1);
    }

    public String decodeToPassword(RefreshTokens token) {
        String credentials = SecurityUtils.decode(token.getValue());
        return credentials.substring(credentials.indexOf(',')+1,credentials.indexOf(':'));
    }

    public String decodeToPassword(String accessToken) {
        RefreshTokens token = getRefreshToken(accessToken);
        String credentials = SecurityUtils.decode(token.getValue());
        return credentials.substring(credentials.indexOf(',')+1,credentials.indexOf(':'));
    }

    public Boolean authenticateUser(String username, String password) {
        String credentials = username+","+password;
        List<String> values = tokensRepository.findAll().stream().map(token -> token.getValue()).toList();
        for (String s : values) {
            SecurityUtils.decode(s);
            if (s.startsWith(credentials)) {
                return true;
            }
        }
        return false;
    }

    public String getAccessToken(RefreshTokens token) {
        String month = LocalDateTime.now().getMonth().toString();
        String accessToken = month+token.getValue();
        return new String(Base64.encodeBase64(accessToken.getBytes(StandardCharsets.UTF_8)));
    }

    public RefreshTokens getRefreshToken(String accessToken) {
        String month = LocalDateTime.now().getMonth().toString();
        String token = new String(Base64.decodeBase64(accessToken.getBytes(StandardCharsets.UTF_8)));
        int index = month.length();
        String refreshToken = token.substring(index);
        return tokensRepository.findByValue(refreshToken);
    }

}
