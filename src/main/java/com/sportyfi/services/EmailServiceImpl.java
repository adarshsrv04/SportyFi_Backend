package com.sportyfi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
    public void sendEmailVerification(String toEmail, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your Sportyfi account");
        message.setText("Click this link to verify your email: " + verificationLink);
        message.setFrom("ayushmehra@kodinghub.online");
        mailSender.send(message);
    }
}
