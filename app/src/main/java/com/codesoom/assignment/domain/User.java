package com.codesoom.assignment.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "id")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updateWith(User source) {
        this.name = source.name;
        this.email = source.email;
        this.password = source.password;
    }
}
