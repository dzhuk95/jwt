package com.example.jwt.service.impl;


import com.example.jwt.model.LoggedUser;
import com.example.jwt.model.exception.WrongTokenException;
import com.example.jwt.orm.UserEntity;
import com.example.jwt.service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import java.util.stream.Collectors;

@Service
// TODO: 30.05.2018 add interface
public class TokenServiceImpl implements TokenService {

    private final String key = "aswrwqetscvzx123";

    private final long tokenLiveDays = 1;

    public Authentication parseToken(String token) {
        Claims claims = getClaims(token, key);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        LoggedUser principal = new LoggedUser(claims, authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String createToken(UserEntity userEntity) {
        return Jwts.builder().claim("role", userEntity.getRole().toString())
                .claim("id", userEntity.getId())
                .claim("username", userEntity.getUsername())
                .claim("password", userEntity.getPassword())
                .setIssuedAt(Date.valueOf(LocalDateTime.now().toLocalDate()))
                .setExpiration(Date.valueOf(LocalDateTime.now().toLocalDate().plusDays(tokenLiveDays)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean validateToken(String token) throws RuntimeException {
        try {
            Claims body = getClaims(token, key);
            return true;
        } catch (SignatureException e) {
            throw new WrongTokenException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new WrongTokenException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new WrongTokenException("Token Expired");
        } catch (UnsupportedJwtException e) {
            throw new WrongTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new WrongTokenException("JWT token compact of handler are invalid");
        }
    }
}
