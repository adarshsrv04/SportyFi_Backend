package com.sportyfi.dto;

import com.sportyfi.entity.UserType;
import com.sportyfi.entity.Users;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
//    private UserType userType;
    private UserDto user;
    
    public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
    
    public AuthResponse(String accessToken2, String refreshToken2, Users user2) {
		// TODO Auto-generated constructor stub
		this.accessToken = accessToken2;
        this.refreshToken = refreshToken2;
//        this.userType = userType2;
        this.user = new UserDto(user2);
        System.out.println(this.user);
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
//	public UserType getUserType() {
//		return userType;
//	}
//	
//	public void setUserType(UserType userType) {
//		this.userType = userType;
//	}

	
}
