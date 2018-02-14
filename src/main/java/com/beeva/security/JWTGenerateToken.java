package com.beeva.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.beeva.model.User;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class JWTGenerateToken {

    public JWTGenerateToken(){

    }

    public String tokenGenerate(User user){
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("beeva");
            token = JWT.create()
                    .withIssuer(user.getUsername())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }

}
