package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    public User userUpdate(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;

        return this;

    }

}

