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
    private String email;
    private String name;
    private String password;

    @Builder
    private User(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
