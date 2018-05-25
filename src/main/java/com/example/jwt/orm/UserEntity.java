package com.example.jwt.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserEntity implements Persistable<Integer> {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Enumerated
    @Getter
    @Setter
    private UserRoles role;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
