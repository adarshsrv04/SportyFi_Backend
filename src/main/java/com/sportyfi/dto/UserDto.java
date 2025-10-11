package com.sportyfi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sportyfi.entity.Users;

public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String userType;
    private Boolean emailVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    
    public UserDto() {}

    // Constructor to convert from Users entity
    public UserDto(Users user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userType = user.getUserType().name();
        this.emailVerified = user.getEmailVerified();
        this.lastLogin = user.getLastLogin();
        this.createdAt = user.getCreatedAt();
    }

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", userType=" + userType
				+ ", emailVerified=" + emailVerified + ", lastLogin=" + lastLogin + ", createdAt=" + createdAt + "]";
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}

