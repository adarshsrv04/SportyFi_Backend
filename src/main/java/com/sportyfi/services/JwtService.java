//package com.sportyfi.services;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//    private final Key secretKey;
//    private final long EXPIRATION_TIME;
//
//    public JwtService(@Value("${jwt.secret}") String secretKey,
//                      @Value("${jwt.expiration}") long expTime) {
//        if (secretKey == null || secretKey.trim().isEmpty()) {
//            throw new IllegalArgumentException("JWT secret key cannot be null or empty");
//        }
//        // Use UTF-8 encoding to ensure consistency
//        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
//        if (keyBytes.length < 32) {
//            throw new IllegalArgumentException("JWT secret key must be at least 32 bytes long for HS256");
//        }
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//        this.EXPIRATION_TIME = expTime;
//    }
//
//    public String extractUserId(String token) {
//        return extractClaim(token, claims -> claims.get("id", String.class));
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isTokenValid(String token) {
//        try {
//            extractAllClaims(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    public String generateToken(String userId, String name, String email, String role) {
//        return Jwts.builder()
//                .claim("id", userId)
//                .claim("name", name)
//                .claim("email", email)
//                .claim("role", role)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(secretKey, SignatureAlgorithm.HS256)
//                .compact();
//    }
//}




package com.sportyfi.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    private final Key secretKey;
    private final long EXPIRATION_TIME;

    public JwtService(@Value("${jwt.secret}") String secretKey,
                     @Value("${jwt.accessTokenExpiration}") long expTime) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("JWT secret is missing or empty");
        }
//        byte[] keyBytes = secretKey.getBytes();
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 64) {
            throw new IllegalArgumentException("JWT secret must be at least 64 bytes for HS512, but got " + keyBytes.length + " bytes");
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.EXPIRATION_TIME = expTime;
        System.out.println("JWT Secret Length: " + keyBytes.length + " bytes"); // Debug
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaim(token, claims -> claims.get("id", String.class)));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT Validation Error: " + e.getMessage()); // Debug
            return false;
        }
    }

    public String generateToken(UUID userId, String name, String email, String role) {
        return Jwts.builder()
                .claim("id", userId)
                .claim("name", name)
                .claim("email", email)
                .claim("role", role)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}