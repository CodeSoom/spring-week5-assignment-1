package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserUpdateDTO {
    @NotEmpty
    private final String name;

    private final String email;

    @NotEmpty
    private final String password;

    @JsonCreator
    public UserUpdateDTO(@JsonProperty("name") String name,
                         @JsonProperty("email") String email,
                         @JsonProperty("password") String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
