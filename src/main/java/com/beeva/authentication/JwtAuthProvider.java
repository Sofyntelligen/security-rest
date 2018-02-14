package com.beeva.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.beeva.authentication.model.JwtAuthenticationToken;
import com.beeva.authentication.model.JwtUserData;
import com.beeva.authentication.model.JwtUserDetails;

import io.jsonwebtoken.JwtException;

@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtUtility jwtUtility;

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		System.out.println("entra a retrieveUser JWTAUTHPROVIDER");
		
		
		JwtAuthenticationToken jwtAuthenticationToken =  (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

		JwtUserData parsedUser = jwtUtility.parseToken(token);

		if (parsedUser == null) {
			throw new JwtException("JWT token is not valid");
		}

		List<GrantedAuthority> authorityList  = 
				AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

		
		System.out.println("Sale de retrieve User: " + parsedUser.toString());
		return new JwtUserDetails(parsedUser.getUserName(), token, authorityList);
	}

}
