package com.example.jwt.service;

import com.example.jwt.orm.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;

public interface TokenService {



    Authentication parseToken(String token);

    default Claims getClaims(String token,String key) {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    String createToken(UserEntity userEntity);

    boolean validateToken(String token);

}
