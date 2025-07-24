package com.sportyfi.dto;

import com.sportyfi.entity.UserType;

public class JwtResponse {
    private String accessToken;
    private String refreshToken;
//    private UserType userType;
    private UserDto user;

    public JwtResponse(AuthResponse authResponse) {
        this.accessToken = authResponse.getAccessToken();
        this.refreshToken = authResponse.getRefreshToken();
//        this.userType = authResponse.getUserType();
        this.user = authResponse.getUser();
    }

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
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

