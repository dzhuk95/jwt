package com.example.jwt;

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
