package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Builder
@Entity
public class User {
    public User() {

    }

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    public void update(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

}
