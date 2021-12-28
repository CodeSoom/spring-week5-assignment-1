package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    private String email;

    @Builder
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void change(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
