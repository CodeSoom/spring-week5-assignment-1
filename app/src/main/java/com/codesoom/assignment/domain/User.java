package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private String password;

    @Builder
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User from(UserData userData) {
        return User.builder()
                .username(userData.getUsername())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
