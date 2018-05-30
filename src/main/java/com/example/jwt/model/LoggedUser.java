package com.example.jwt.model;

import com.example.jwt.orm.UserEntity;
import com.example.jwt.orm.UserRoles;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoggedUser extends User {

    @Getter
    @Setter
    private UserEntity userEntity;

    public LoggedUser(Claims claims, Collection<? extends GrantedAuthority> authority) {
        super(claims.get("username", String.class), claims.get("password", String.class), authority);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(claims.get("id", Integer.class));
        this.userEntity = userEntity;
    }

    public static int id() {
        LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedUser == null ? 0 : loggedUser.getUserEntity().getId();
    }
}
