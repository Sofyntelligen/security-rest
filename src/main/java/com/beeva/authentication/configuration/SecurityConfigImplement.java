package com.beeva.authentication.configuration;

import com.beeva.authentication.JWTAuthenticationLogin;
import com.beeva.authentication.JWTAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

public interface SecurityConfigImplement {

    public JWTAuthenticationLogin getJWTAuthenticationLogin(String url, AuthenticationManager authenticationManager);

    public JWTAuthenticationToken getJWTAuthenticationToken();

}
