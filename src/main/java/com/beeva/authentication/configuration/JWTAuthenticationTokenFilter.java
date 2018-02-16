package com.beeva.authentication.configuration;

import com.beeva.authentication.repository.implement.TokenImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    TokenImplement tokenImplement;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String user = tokenImplement.readToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));


        Authentication authentication =
                new UsernamePasswordAuthenticationToken("user", null, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);

    }
}