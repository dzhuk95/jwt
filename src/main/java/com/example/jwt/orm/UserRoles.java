package com.example.jwt.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum UserRoles implements GrantedAuthority {
    USER1(0), USER2(1);
    @Getter
    private int id;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
