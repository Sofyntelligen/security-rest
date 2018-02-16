package com.beeva.authentication.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.beeva.authentication.model.JwtUser;
import com.beeva.authentication.repository.implement.TokenImplement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

public class JWTAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    TokenImplement tokenImplement;

    public JWTAuthenticationLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException {

        InputStream inputStream = httpServletRequest.getInputStream();
        JwtUser user = new ObjectMapper().readValue(inputStream, JwtUser.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                )
        );

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                            FilterChain filterChain, Authentication authentication) {

        try {

            httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION, tokenImplement.generateToken(authentication.getName(), Algorithm.HMAC256("beeva")));

        } catch (UnsupportedEncodingException exception) {
            exception.getStackTrace();
        }

    }
}