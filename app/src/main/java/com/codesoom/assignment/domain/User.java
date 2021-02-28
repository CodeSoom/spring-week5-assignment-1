package com.codesoom.assignment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    Long id;
    String email;
    String name;
    String password;

    public User(
            Long id,
            String email,
            String name,
            String password
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(
            String email,
            String name,
            String password
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void Change(User source) {
        this.email = source.email();
        this.name = source.name();
        this.password = source.password();
    }
}
