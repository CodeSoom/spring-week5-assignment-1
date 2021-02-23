package com.codesoom.assignment.domain;

public class User {
    private final Long id;
    private String name;
    private String mail;
    private String password;

    public User(Long id, String name, String mail, String password) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void changeName(String newName) {
        this.name = newName;
    }
}
