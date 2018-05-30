package com.example.jwt.model.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AuthReq {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
}
