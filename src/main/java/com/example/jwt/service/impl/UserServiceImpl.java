package com.example.jwt.service.impl;

import com.example.jwt.model.api.AuthReq;
import com.example.jwt.jpa.UserRepository;
import com.example.jwt.orm.UserEntity;
import com.example.jwt.service.UserService;
import com.example.jwt.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenServiceImpl tokenService;

    public ResponseEntity createToken(AuthReq authReq) {
        Optional<UserEntity> firstByUsernameAndPassword = userRepository.findFirstByUsernameAndPassword(authReq.getUsername(), authReq.getPassword());
        if (!firstByUsernameAndPassword.isPresent())
            return ResponseEntity.notFound().build();
        UserEntity userEntity = firstByUsernameAndPassword.get();
        String compact = tokenService.createToken(userEntity);
        return ResponseEntity.ok(compact);
    }
}
