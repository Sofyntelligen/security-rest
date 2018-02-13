package com.beeva;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends AbstractAuthenticationProcessingFilter {

	protected JwtFilter() {
		super("/**");
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	    super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

//		String header = request.getHeader("Authorization");

		String header = "Bearer Hola";
//		if (header == null || !header.startsWith("Bearer ")) {
//			throw new JwtTokenMissingException("No JWT token found in request headers");
//		}

		//clave a comprobar, para pruebas solo "hola"
//		String authToken = header.substring(7);

		//Implementar con interfaz Authentication.class
		//solo necesitamos que regrese que "esta autenticado"
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		
		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authorities);

		return getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to continue
		// the request normally
		// and return the response as if the resource was not secured at all
		chain.doFilter(request, response);
	}

}
