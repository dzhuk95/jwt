package com.example.jwt.web;

import com.example.jwt.AuthReq;
import com.example.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/")
public class Controller {
    @Autowired
    UserService userService;

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity auth(@RequestBody AuthReq authReq) {
        return userService.createToken(authReq);
    }

    @GetMapping("/getString")
    public ResponseEntity getString() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
