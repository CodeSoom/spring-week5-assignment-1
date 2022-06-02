package com.codesoom.assignment.dto;

import com.codesoom.assignment.validation.MyPassword;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserSignupData {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String name;

    @MyPassword
    private final String password;

    @Builder
    @JsonCreator
    public UserSignupData(@JsonProperty("email") String email,
                          @JsonProperty("name") String name,
                          @JsonProperty("password") String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
