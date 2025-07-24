package com.sportyfi.dao;

import com.sportyfi.entity.Users;

public interface SportyfiUserDao {

	void saveUser(Users user);

	boolean existsByEmail(String email);

	void updateUser(Users user);

	Users findByEmail(String email);

}
