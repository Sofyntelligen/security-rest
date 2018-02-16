package com.beeva.authentication.repository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beeva.authentication.configuration.JwtToken;
import com.beeva.authentication.repository.implement.TokenImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class JWTTokenReporitory implements TokenImplement {

    private static final Logger log = LoggerFactory.getLogger(JwtToken.class);

    @Override
    public String generateToken(String user, Algorithm algoritmo) {
        String token = null;
        try {

            token = JWT.create()
                    .withIssuer(user)
                    .sign(algoritmo);

        } catch (JWTCreationException exception) {
            log.error("--- error --- " + exception.getStackTrace());
        }
        return token;
    }

    @Override
    public String readToken(String token) {

        DecodedJWT decodedJWT = null;

        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(6);
            }
            decodedJWT = JWT.decode(token);
        } catch (JWTVerificationException exception) {
            log.error("--- error --- " + exception.getStackTrace());
        }

        return decodedJWT.getIssuer().toString();
    }

}
