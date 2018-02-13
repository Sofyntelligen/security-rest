package com.beeva;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		System.out.println(">>>> JWTEntryPOINT COMMENCE");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//		response.sendError(HttpServletResponse.SC_OK, "Unauthorized");

	}

}
