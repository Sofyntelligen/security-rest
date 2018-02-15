package com.beeva.authentication.repository;

import com.beeva.authentication.model.JwtUser;

public interface UsersRepository {

	public JwtUser getUser (String username, String password);
	
}
