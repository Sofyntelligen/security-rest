package com.beeva.authentication;

import org.springframework.beans.factory.annotation.Value;
	import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;

import com.beeva.authentication.model.JwtUserData;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtility {

	@Value("${jwt.secret}")
	private String secret;

	
	public JwtUserData parseToken(String token) {
		JwtUserData user = null;
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			user = new JwtUserData();
			user.setUserName(body.getSubject());
			user.setRole((String)body.get("role")); 
			
			return user;

		} catch (JwtException | ClassCastException e) {
			return null;
		}
	}

	public String generateToken(JwtUserData user) {
		Claims claims = Jwts.claims().setSubject(user.getUserName());
				claims.put("role", user.getRole());

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

}
