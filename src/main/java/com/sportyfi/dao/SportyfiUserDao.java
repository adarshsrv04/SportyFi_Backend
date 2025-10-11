package com.sportyfi.dao;

import java.util.UUID;

import com.sportyfi.entity.Users;

public interface SportyfiUserDao {

	void saveUser(Users user);

	boolean existsByEmail(String email);

	void updateUser(Users user);

	Users findByEmail(String email);
	
	public Users getUserById(UUID userId);

	void saveGoogleUser(Users user, String name);

}
