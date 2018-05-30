package com.example.jwt.service;

import com.example.jwt.model.api.AuthReq;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity createToken(AuthReq authReq);
}
