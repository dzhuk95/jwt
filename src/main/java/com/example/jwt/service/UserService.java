package com.example.jwt.service;

import com.example.jwt.AuthReq;
import com.example.jwt.jpa.UserRepository;
import com.example.jwt.model.LoggedUser;
import com.example.jwt.orm.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity createToken(AuthReq authReq) {
        Optional<UserEntity> firstByUsernameAndPassword = userRepository.findFirstByUsernameAndPassword(authReq.getUsername(), authReq.getPassword());
        if (!firstByUsernameAndPassword.isPresent())
            return ResponseEntity.notFound().build();
        UserEntity userEntity = firstByUsernameAndPassword.get();
        String compact = tokenService.createToken(userEntity);
        return ResponseEntity.ok(compact);
    }
}
