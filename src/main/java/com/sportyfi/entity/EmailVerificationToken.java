package com.sportyfi.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "email_verification_tokens", schema = "auth_spring")
public class EmailVerificationToken {

    @Id
    private UUID token;

    @Column(nullable = false)
    private String email;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    // Constructor
    public EmailVerificationToken(String email, Duration validityDuration) {
        this.token = UUID.randomUUID();
        this.email = email;
        this.expiryDate = LocalDateTime.now().plus(validityDuration);
    }

    public EmailVerificationToken() {} // for JPA

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
}

