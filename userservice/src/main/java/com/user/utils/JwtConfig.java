package com.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtConfig {

    @Value("${jwt.secret:d2VjcmV0LWtleS10aGF0LWlzLWF0LWxlYXN0LTMyLWJ5dGVzLWxvbmc==}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private long validityInMilliseconds;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        String trimmedSecret = secretKey.trim();
        try {
            //decode as Base64 first
            byte[] keyBytes = Base64.getDecoder().decode(trimmedSecret);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            // If not Base64, use as plain text
            byte[] keyBytes = trimmedSecret.getBytes(StandardCharsets.UTF_8);

            // For HS256, we need at least 256-bit key (32 bytes)
            if (keyBytes.length < 32) {
                // Use the string key directly - JJWT will handle padding internally
                this.key = Keys.hmacShaKeyFor(keyBytes);
            } else {
                // Use the first 32 bytes if longer
                byte[] truncatedKey = new byte[32];
                System.arraycopy(keyBytes, 0, truncatedKey, 0, 32);
                this.key = Keys.hmacShaKeyFor(truncatedKey);
            }
        }
    }

    public String createToken(String username, Map<String, Object> claims) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key, Jwts.SIG.HS256) // Updated signing method
                .compact();
    }

    public String createToken(String username) {
        return createToken(username, new HashMap<>());
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = getUsername(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) {
        try {
            // Try to parse the token to validate it
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to generate a secure secret key
    public static String generateSecureKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public Map<String, Object> getTokenClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Map<String, Object> claimsMap = new HashMap<>();
            claims.forEach(claimsMap::put);
            return claimsMap;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse token claims", e);
        }
    }
}