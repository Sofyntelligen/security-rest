package com.beeva.authentication;

import com.beeva.authentication.JWTAuthenticationLogin;
import com.beeva.authentication.JWTAuthenticationToken;
import com.beeva.authentication.configuration.SecurityConfigImplement;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityType implements SecurityConfigImplement {

    @Override
    public JWTAuthenticationLogin getJWTAuthenticationLogin(String url, AuthenticationManager authenticationManager) {
        return new JWTAuthenticationLogin(url, authenticationManager);
    }

    @Override
    public JWTAuthenticationToken getJWTAuthenticationToken() {
        return new JWTAuthenticationToken();
    }
}
