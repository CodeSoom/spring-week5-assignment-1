package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;

    private String name;
    private String email;
    private String password;

    protected User() {
    }

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
