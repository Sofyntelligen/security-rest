package com.beeva.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
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
    }

    public static Authentication getToken(HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization");

        if (token != null) {
            String user = "";

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
                    null;
        }
        return null;
    }
}