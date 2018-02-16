package com.beeva.authentication.repository;

import com.beeva.authentication.configuration.JWTAuthenticationLoginFilter;
import com.beeva.authentication.configuration.JWTAuthenticationTokenFilter;
import com.beeva.authentication.repository.implement.FilterImplement;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityTypeRepository implements FilterImplement {

    @Override
    public JWTAuthenticationLoginFilter getJWTAuthenticationLogin(String url, AuthenticationManager authenticationManager) {
        return new JWTAuthenticationLoginFilter(url, authenticationManager);
    }

    @Override
    public JWTAuthenticationTokenFilter getJWTAuthenticationToken() {
        return new JWTAuthenticationTokenFilter();
    }
}
