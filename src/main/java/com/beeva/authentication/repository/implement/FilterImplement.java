package com.beeva.authentication.repository.implement;

import com.beeva.authentication.configuration.JWTAuthenticationLoginFilter;
import com.beeva.authentication.configuration.JWTAuthenticationTokenFilter;
import org.springframework.security.authentication.AuthenticationManager;

public interface FilterImplement {

    public JWTAuthenticationLoginFilter getJWTAuthenticationLogin(String url, AuthenticationManager authenticationManager);

    public JWTAuthenticationTokenFilter getJWTAuthenticationToken();

}
