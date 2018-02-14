package com.beeva.authentication.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private String token;

	public JwtAuthenticationToken(String token) {
		super(null, null);
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	// Metodo no utilizado para esta authenticacion
	@Override
	public Object getPrincipal() {
		return null;
	}

	// Metodo no utilizado para esta authenticacion
	@Override
	public Object getCredentials() {
		return null;
	}

}
