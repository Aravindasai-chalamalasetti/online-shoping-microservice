package com.user.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JWTTokenProvider {
    private JWTTokenProvider() {
    }

    // Generate a secure key from the secret string
    private static final SecretKey SECRET_KEY = createSecretKey();

    private static SecretKey createSecretKey() {
        try {
            // Decode the Base64 string from TokenConstant
            byte[] keyBytes = Base64.getDecoder().decode(TokenConstant.AUTHORITIES.getBytes());
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create secret key", e);
        }
    }

    public static String generateToken(final String uid,final String roleName) {
        Date issuedDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issuedDate);
        calendar.add(Calendar.YEAR, 1);
        Date expiredDate = calendar.getTime();
        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenConstant.UUID, uid);
        claims.put("role", roleName);
        try {
            return Jwts.builder()
                    .subject(uid)
                    .claims(claims)
                    .issuer(uid)
                    .issuedAt(issuedDate)
                    .expiration(expiredDate)
                    .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    public static String authenticate(final String token, final String user) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build();

            Jws<Claims> jwsClaims = parser.parseSignedClaims(token);
            return jwsClaims.getPayload().get(user, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate token", e);
        }
    }

    public static boolean isValid(final String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build();

            Jws<Claims> jwsClaims = parser.parseSignedClaims(token);
            Date expiration = jwsClaims.getPayload().getExpiration();
            return new Date().before(expiration);
        } catch (Exception e) {
            return false;
        }
    }

    public static Claims getClaims(final String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build();

            return parser.parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get claims from token", e);
        }
    }

    public static String getUuidFromToken(final String token) {
        Claims claims = getClaims(token);
        return claims.get(TokenConstant.UUID, String.class);
    }

    public static String getRoleFromToken(final String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
}