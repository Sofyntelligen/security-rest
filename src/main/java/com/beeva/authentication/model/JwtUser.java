package com.beeva.authentication.model;

public class JwtUser {

	private String username;
	private String role;

	public JwtUser(String username, String role) {
		this.username = username;
		this.role = role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User{" + '\'' + ", username='" + username + '\'' + '}';
	}
}
