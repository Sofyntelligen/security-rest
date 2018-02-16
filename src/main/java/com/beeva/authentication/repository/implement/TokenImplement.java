package com.beeva.authentication.repository.implement;

import com.auth0.jwt.algorithms.Algorithm;

public interface TokenImplement {

    public String generateToken(String user, Algorithm algoritmo);

    public String readToken(String token);

}
