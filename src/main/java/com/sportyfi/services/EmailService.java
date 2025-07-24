package com.sportyfi.services;

public interface EmailService {

	public void sendEmailVerification(String to, String verificationLink);
}
