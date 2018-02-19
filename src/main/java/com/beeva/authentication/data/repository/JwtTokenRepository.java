package com.beeva.authentication.data.repository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beeva.authentication.data.UserDAO;
import com.beeva.authentication.data.UserDatabaseDAO;
import com.beeva.authentication.model.DatabaseUser;
import com.beeva.authentication.model.JwtUser;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenRepository implements UserDAO {

	@Autowired
	UserDatabaseDAO theUserDatabase;

	private String secreto = "beeva123&$%$&/";

	@Override
	public Optional<JwtUser> validateUser(HttpServletRequest request) {

		String token = request.getHeader("Authorization");

		DecodedJWT decodedJWT = null;

		try {
			if (token != null && token.startsWith("Bearer ")) {
				
				token = token.substring(6);
				
				Algorithm hashAlgorithm = Algorithm.HMAC256(secreto);
				
//				decodedJWT = JWT.require(hashAlgorithm).build().verify(token);
				decodedJWT = JWT.decode(token);
				
			}
			
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException exception) {

			System.out.println("--- error --- ");
			exception.printStackTrace();
			return Optional.ofNullable(null);
		}


		 return Optional.ofNullable(new JwtUser(
		 decodedJWT.getSubject(),
		 decodedJWT.getClaim("role").toString()));
 
	}

	@Override
	public Optional<String> generateToken(Authentication authentication) {
		String token = null;

		try {

			Algorithm hashAlgorithm = Algorithm.HMAC256(secreto);

			token = JWT.create().withSubject(authentication.getName().toString())
					.withClaim("role", authentication.getAuthorities().stream().findFirst().get().toString())
					.sign(hashAlgorithm);

		} catch (UnsupportedEncodingException exception) {
			System.out.println("--- error --- " + exception.getStackTrace());
		} catch (JWTCreationException exception) {
			System.out.println("--- error --- " + exception.getStackTrace());
		}

		return Optional.ofNullable("Bearer: " + token);
	}

}
