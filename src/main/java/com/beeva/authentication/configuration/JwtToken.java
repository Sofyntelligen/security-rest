package com.beeva.authentication.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;

public class JwtToken {

    private static final Logger log = LoggerFactory.getLogger(JwtToken.class);

    @NotNull
    public static void generateToken(HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = null;
        try {

            Algorithm algorithm = Algorithm.HMAC256("beeva");
            token = JWT.create().withIssuer(authentication.getName()).sign(algorithm);

        } catch (UnsupportedEncodingException exception){
            log.error("--- error --- " + exception.getStackTrace());
        } catch (JWTCreationException exception){
            log.error("--- error --- " + exception.getStackTrace());
        }

        httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION, token);
    }

    @NotNull
    public static Authentication getToken(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                
        if (token != null && token.startsWith("Bearer ")) {
            
        	token = token.substring(6);
        	DecodedJWT decodedJWT = null;

            try {
                decodedJWT = JWT.decode(token);
            } catch (JWTVerificationException exception){
                log.error("--- error --- " + exception.getStackTrace());
                throw new JWTVerificationException("Error decoding token");
            }

            return (decodedJWT != null) ?
            		new UsernamePasswordAuthenticationToken("josedaniel", null, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")):
                    null;
        }
        return null;
    }
}