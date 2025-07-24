package com.sportyfi.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sportyfi.dao.SportyfiUserDao;
import com.sportyfi.dao.VerificationTokenDao;
import com.sportyfi.dto.AuthResponse;
import com.sportyfi.dto.LoginRequest;
import com.sportyfi.entity.EmailVerificationToken;
import com.sportyfi.entity.UserType;
import com.sportyfi.entity.Users;
import com.sportyfi.entity.VerificationToken;
import com.sportyfi.exception.EmailNotVerifiedException;
import com.sportyfi.utilities.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private SportyfiUserDao userDao;

	@Autowired
	private VerificationTokenDao tokenDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Override
	@Transactional
	public void registerUser(Users user, EmailVerificationToken token) {
		if (userDao.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already registered");
		}
		userDao.saveUser(user);
		tokenDao.saveToken(token);
	}

	@Override
	@Transactional
	public void register(String email, String password, UserType userType) {
		Users user = new Users();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setUserType(userType);
		System.out.println(user.getUserType());
		userDao.saveUser(user);
		EmailVerificationToken token = new EmailVerificationToken(user.getEmail(), Duration.ofHours(24));
		tokenDao.saveToken(token);
		String verificationLink = "http://localhost:8080/sportyfi/auth/verify-email?token=" + token.getToken();
		emailService.sendEmailVerification(user.getEmail(), verificationLink);

		//        String token = UUID.randomUUID().toString();
		//        VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusDays(1));
		//        tokenDao.saveToken(token);
		//        emailService.sendVerificationEmail(user.getEmail(), token);
	}

	//	@Transactional
	//	public void verifyEmail(String tokenString) {
	//		EmailVerificationToken token = tokenDao.findByToken(tokenString);
	//		if (token == null || token.getExpiryDate().isBefore(LocalDateTime.now())) {
	//			throw new RuntimeException("Invalid or expired token");
	//		}
	//
	//		Users user = token.getUser();
	//		user.setEmailVerified(true);
	//		user.setLastLogin(LocalDateTime.now());
	//		userDao.updateUser(user);
	//
	//		tokenDao.deleteToken(token);
	//	}


	//	@Override
	//	public void verifyToken(String token) {
	//		VerificationToken vt = tokenRepo.findByToken(token)
	//				.orElseThrow(() -> new RuntimeException("Invalid token"));
	//
	//		User user = vt.getUser();
	//		user.setEmailVerified(true);
	//		userRepo.save(user);
	//		tokenRepo.delete(vt);
	//	}

	@Override
	@Transactional
	public AuthResponse login(LoginRequest loginRequest) {
		Users user = userDao.findByEmail(loginRequest.getEmail());
		if (user == null) {
			throw new RuntimeException("Invalid email or password");
		}

		if (!user.getEmailVerified()) {
			throw new EmailNotVerifiedException("Email not verified");
		}

		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid email or password");
		}

		// Update last login
		user.setLastLogin(LocalDateTime.now());
		userDao.updateUser(user);

		// Generate JWT tokens
		String accessToken = jwtUtil.generateAccessToken(user);
		String refreshToken = jwtUtil.generateRefreshToken(user);
		System.out.println(accessToken + "--" + refreshToken);
		return new AuthResponse(accessToken, refreshToken, user);
	}

}
