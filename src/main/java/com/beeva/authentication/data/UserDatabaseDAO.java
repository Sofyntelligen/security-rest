package com.beeva.authentication.data;

import java.util.Optional;

import com.beeva.authentication.model.DatabaseUser;

public interface UserDatabaseDAO {

	public Optional<DatabaseUser> getUser(String username);
		
}
