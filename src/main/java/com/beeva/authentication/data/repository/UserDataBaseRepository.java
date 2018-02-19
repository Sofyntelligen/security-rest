package com.beeva.authentication.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.beeva.authentication.data.UserDatabaseDAO;
import com.beeva.authentication.model.DatabaseUser;

@Component
public class UserDataBaseRepository implements UserDatabaseDAO {

	List<DatabaseUser> users = new ArrayList<>();
		
	public UserDataBaseRepository() {
		users.add(new DatabaseUser("Mario", "123", "ROLE_ADMIN"));
		users.add(new DatabaseUser("josedaniel", "beeva", "ROLE_USER"));
	}

	@Override
	public Optional<DatabaseUser> getUser(String username) {
		return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
	}

}
