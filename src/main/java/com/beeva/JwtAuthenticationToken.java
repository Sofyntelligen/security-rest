package com.beeva;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object getCredentials() {
		return "password";
	}

	@Override
	public Object getPrincipal() {
		return "user";
	}
	
}
