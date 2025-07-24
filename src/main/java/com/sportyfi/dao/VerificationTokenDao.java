package com.sportyfi.dao;

import com.sportyfi.entity.EmailVerificationToken;

public interface VerificationTokenDao {

	void saveToken(EmailVerificationToken token);

	EmailVerificationToken findByToken(String tokenString);

	void deleteToken(String token);

}
