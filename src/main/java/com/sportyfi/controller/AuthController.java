package com.sportyfi.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.sportyfi.dao.SportyfiUserDao;
import com.sportyfi.dao.VerificationTokenDao;
import com.sportyfi.dto.AuthResponse;
import com.sportyfi.dto.LoginRequest;
import com.sportyfi.dto.SignupRequest;
import com.sportyfi.dto.UserDto;
import com.sportyfi.entity.EmailVerificationToken;
import com.sportyfi.entity.UserType;
import com.sportyfi.entity.Users;
import com.sportyfi.services.AuthService;
import com.sportyfi.services.EmailService;
import com.sportyfi.utilities.GoogleTokenVerifier;
import com.sportyfi.utilities.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/sportyfi/auth")
public class AuthController {

    @Autowired private AuthService authService;
    
    @Autowired private EmailService emailService;
    
    @Autowired private VerificationTokenDao tokenDao;
    
    @Autowired
    private SportyfiUserDao userDao;
    
    @Autowired JwtUtil jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
    	System.out.println(req.getEmail() + req.getPassword());
        authService.register(req.getEmail(), req.getPassword(), req.getUserType());
        return ResponseEntity.ok("Verification email sent");
    }

    @Transactional
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String tokenStr) {
        EmailVerificationToken token = tokenDao.findByToken(tokenStr);
        if (token == null) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired.");
        }

        Users user = userDao.findByEmail(token.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        user.setEmailVerified(true);
        userDao.updateUser(user);
        tokenDao.deleteToken(tokenStr);

        return ResponseEntity.ok("Email verified successfully!");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest req) {
        AuthResponse jwt = authService.login(req);
        return ResponseEntity.ok((jwt));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String email = jwtService.extractUsername(refreshToken);
        Users user = userDao.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String newAccessToken = jwtService.generateAccessToken(user);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
            }

            String token = authHeader.substring(7);
            String email = jwtService.extractUsername(token);
            if (email == null || !jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
            }

            Users user = userDao.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(new UserDto(user));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token expired");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/secure-data")
    public ResponseEntity<?> getSecureData(Authentication authentication) {
        String email = authentication.getName(); // Retrieved from token subject
        return ResponseEntity.ok("Hello " + email + ", this is protected data.");
    }
    
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        System.out.println("idToken: " + idToken);
        GoogleIdToken.Payload payload = GoogleTokenVerifier.verifyToken(idToken);

        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        // Check if user exists
        Users user = userDao.findByEmail(email);
        if (user == null) {
            // Create new user
            user = new Users();
            user.setEmail(email);
//            user.setFullName(name);
            user.setEmailVerified(true);
            user.setUserType(UserType.USER);
            user.setCreatedAt(LocalDateTime.now());
//            user.setUpdatedAt(LocalDateTime.now());
            userDao.saveUser(user);
        }

        // Generate JWT tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, user));
    }

}

