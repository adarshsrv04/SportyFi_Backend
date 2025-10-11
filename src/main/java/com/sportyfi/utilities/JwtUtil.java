package com.sportyfi.utilities;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sportyfi.entity.Users;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.accessTokenExpiration}")
//    private long accessTokenExpiration;
//
    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;
	
	private final Key jwtSecret;
    private final long accessTokenExpiration;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret,
                      @Value("${jwt.accessTokenExpiration}") long accessTokenExpiration) {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 64) {
            throw new IllegalArgumentException("JWT secret must be at least 64 bytes for HS512, got " + keyBytes.length + " bytes");
        }
        this.jwtSecret = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        System.out.println("AuthService JWT Secret Length: " + keyBytes.length + " bytes"); // Debug
    }

//    public String generateAccessToken(Users user) {
//        return Jwts.builder()
//                .setSubject(user.getEmail())
//                .claim("id", user.getId())
//                .claim("role", user.getUserType())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
    
    public String generateAccessToken(Users user) {
//        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8); // Use UTF-8 for consistency
//        if (keyBytes.length < 64) {
//            throw new IllegalArgumentException("JWT secret must be at least 64 bytes for HS512");
//        }
//        Key signingKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getUserType())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(this.jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
    
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}