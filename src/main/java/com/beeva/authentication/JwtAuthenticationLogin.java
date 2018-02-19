package com.beeva.authentication;

import com.beeva.authentication.data.UserDAO;
import com.beeva.authentication.data.repository.JwtTokenRepository;
import com.beeva.authentication.model.JwtJSONUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class JwtAuthenticationLogin extends AbstractAuthenticationProcessingFilter {

	
    public JwtAuthenticationLogin(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException {

        InputStream inputStream = httpServletRequest.getInputStream();
        JwtJSONUser user = new ObjectMapper().readValue(inputStream, JwtJSONUser.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole())));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                            FilterChain filterChain, Authentication authentication) throws IOException, ServletException {

    	UserDAO theUserDao = new JwtTokenRepository(); 
        httpServletResponse.addHeader("Authorization", theUserDao.generateToken(authentication).get());
        httpServletResponse.addHeader("content-type", "application/json");
        httpServletResponse.setStatus(httpServletResponse.SC_OK);

    }
}