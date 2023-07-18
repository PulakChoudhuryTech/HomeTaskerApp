package com.smarthome.hometasker.utils;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class AuthenticationTokenGenerator {

    private static final int TOKEN_LENGTH = 64; // Length of the token in bytes
    private static final long TOKEN_EXPIRATION_MINUTES = 5; // Expiration time in minutes

    public static String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public static LocalDateTime getTokenExpiration() {
        return LocalDateTime.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES);
    }

    public static boolean isTokenExpired(LocalDateTime expirationDateTime) {
        return LocalDateTime.now().isAfter(expirationDateTime);
    }

}
