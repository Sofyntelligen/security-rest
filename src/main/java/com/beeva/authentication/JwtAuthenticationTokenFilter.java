package com.beeva.authentication;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.beeva.authentication.data.UserDAO;
import com.beeva.authentication.data.repository.JwtTokenRepository;
import com.beeva.authentication.model.JwtUser;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationTokenFilter() {
		super("/**");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		UserDAO theUserDao = new JwtTokenRepository();

		System.out.println(">>>>Inicia attemptAuth");
		Optional<JwtUser> validatedUser = theUserDao.validateUser(request);

		if (validatedUser.isPresent()) {
			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(
							validatedUser.get().getUsername(), null,
							AuthorityUtils.createAuthorityList(validatedUser.get().getRole())));
		}
		
		throw new BadCredentialsException("Credenciales no autentificadas");

		// TODO validar el resultado del usuario, si el usuario no esta autenticado
		// tirar un AuthenticationException
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		super.successfulAuthentication(request, response, chain, authResult);

//		chain.doFilter(request, response);

	}

}
