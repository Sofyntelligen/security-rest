package com.beeva.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.beeva.authentication.model.JwtUserDetails;

@Component
public class JwtAuthenticationTokenProvider extends AbstractUserDetailsAuthenticationProvider {


    @Override
    public boolean supports(Class<?> authentication) {
//        return (JwtAuthenticationToken.class.isAssignableFrom(authentication) );
    	return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
       
//    	JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
//       
//        String token = jwtAuthenticationToken.getToken();
//
//        User parsedUser = jwtUtil.parseToken(token);
//
//        if (parsedUser == null) {
//            throw new JwtTokenMalformedException("JWT token is not valid");
//        }
//
//        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");

        List authorities = new ArrayList<>(authentication.getAuthorities());
        return new JwtUserDetails(authentication.getPrincipal().toString(), null, authorities);
    }


}
