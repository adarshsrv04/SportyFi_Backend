package com.sportyfi.exception;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleExpiredJwt(ExpiredJwtException ex) {
		System.out.println("JWT expired: " + ex.getMessage());
		return ResponseEntity.status(401).body("Access token has expired. Please refresh.");
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<?> handleJwtException(JwtException ex) {
		return ResponseEntity.status(401).body("Invalid or malformed JWT token.");
	}
	
	@ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<?> handleEmailNotVerified(EmailNotVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(Collections.singletonMap("error", ex.getMessage()));
    }
}

