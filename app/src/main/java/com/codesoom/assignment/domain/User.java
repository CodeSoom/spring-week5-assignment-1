package com.codesoom.assignment.domain;

public class User {
    private Long id;
    private String name;
    private String email;
    private int password;

    public User(Long id, String name, String email, int password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPassword() {
        return password;
    }
}
