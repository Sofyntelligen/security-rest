package com.beeva.authentication.model;

public class JwtJSONUser {

    private String username;
    private String password;
    private String role;

    public String getRole() {
		return role;
	}
    
    public void setRole(String role) {
		this.role = role;
	}
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
