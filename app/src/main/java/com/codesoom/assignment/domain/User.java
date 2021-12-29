package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String pw;

    public User() {

    }

    public User(Long id, String name, String email, String pw) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pw = pw;
    }

    public User(String name, String email, String pw) {
        this.name = name;
        this.email = email;
        this.pw = pw;
    }

    public void changeWith(User source) {
        this.name = source.getName();
        this.email = source.getEmail();
        this.pw = source.getPw();
    }
}
