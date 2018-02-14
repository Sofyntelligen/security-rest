package com.beeva.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

public class JWTToken {

    private static final Logger log = LoggerFactory.getLogger(JWTToken.class);

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

        httpServletResponse.addHeader("Authorization", "bearer : " + token);
        httpServletResponse.addHeader("content-type", "application/json");
    }

    public static Authentication getToken(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization");

        if (token != null) {
            DecodedJWT decodedJWT = null;
            try {
                decodedJWT = JWT.decode(token);;
            } catch (JWTVerificationException exception){
                //Invalid signature/claims
            }

            return decodedJWT != null ?
                    new UsernamePasswordAuthenticationToken(decodedJWT.getIssuer(), null, Collections.emptyList()) :
                    null;
        }
        return null;
    }
}