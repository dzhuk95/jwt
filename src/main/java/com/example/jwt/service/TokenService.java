package com.example.jwt.service;

import com.example.jwt.orm.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;

public interface TokenService {

    String key = "aswrwqetscvzx123";

    long tokenLiveDays = 1;


    Authentication parseToken(String token);

    default Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    String createToken(UserEntity userEntity);

    boolean validateToken(String token);

}
