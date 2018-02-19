package com.beeva.authentication.data;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.beeva.authentication.model.JwtUser;

public interface UserDAO {

	public Optional<JwtUser> validateUser (HttpServletRequest request);
	
	public Optional<String> generateToken (Authentication authentication);
	
}
