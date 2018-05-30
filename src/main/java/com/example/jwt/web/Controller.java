package com.example.jwt.web;

import com.example.jwt.model.api.AuthReq;
import com.example.jwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class Controller {
    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity auth(@RequestBody AuthReq authReq) {
        return userService.createToken(authReq);
    }

    @GetMapping("/getString")
    public ResponseEntity getString() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }

    @GetMapping("/exception")
    public void getException() {
        throw new IllegalArgumentException("You got caught");
    }
}
