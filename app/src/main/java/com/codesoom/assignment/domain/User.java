package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Entity
public class User {

    @NotBlank(message = "{username.notBlank}")
    @NotNull(message = "{username.notNull}")
    @Id
    private String username;

    @NotBlank(message = "{password.notBlank}")
    @NotNull(message = "{password.notNull}")
    private String password;

    @NotBlank(message = "{email.notBlank}")
    @NotNull(message = "{email.notNull}")
    @Email
    private String email;

    @Builder
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    protected User() {
    }
}
