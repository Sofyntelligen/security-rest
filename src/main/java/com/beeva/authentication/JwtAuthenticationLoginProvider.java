package com.beeva.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtAuthenticationLoginProvider extends AbstractUserDetailsAuthenticationProvider{

	@Override
    public boolean supports(Class<?> authentication) {
    	return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    	
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        User user = new User(authentication.getPrincipal().toString(), authentication.getCredentials().toString(), authorities); 
        
        
        System.out.println(user.getUsername() + user.getPassword() + user.getAuthorities() );
        return new User(authentication.getPrincipal().toString(),  authentication.getCredentials().toString(), authorities);
    }
}
