package com.beeva.authentication.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.beeva.authentication.model.JwtAuthenticationToken;

import io.jsonwebtoken.JwtException;


public class JwtFilter extends AbstractAuthenticationProcessingFilter {

	public JwtFilter() {
		super("/rest/**");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		//Recupera la propiedad "Authorization" del header del request
		String header = request.getHeader("Authorization");

//		String header = "Bearer 123";
		//Valida que el inicio del valor recuperado corresponda a un Bearer
		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtException("No JWT token found in request headers");
		}

		String authToken = header.substring(7);

		
		
		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
		
		return getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to continue
		// the request normally
		// and return the response as if the resource was not secured at all
		System.out.println(">>>>successfulAuth JWTFILTER");

		chain.doFilter(request, response);
	}
	
//	@Override
//	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException failed) throws IOException, ServletException {
//		System.out.println(">>>>unsuccessfulAuthentication JTWFilter");
//		super.unsuccessfulAuthentication(request, response, failed);
//	}

}
