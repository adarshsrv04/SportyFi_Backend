package com.sportyfi.services;

import com.sportyfi.dto.AuthResponse;
import com.sportyfi.dto.LoginRequest;
import com.sportyfi.entity.EmailVerificationToken;
import com.sportyfi.entity.UserType;
import com.sportyfi.entity.Users;

public interface AuthService {
	
	void registerUser(Users user, EmailVerificationToken token);
//	public void verifyToken(String token);
	public AuthResponse login(LoginRequest loginRequest);
	void register(String email, String password, UserType userType);
}
