package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.users.UserUpdateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Builder
public class UserUpdateRequestData implements UserUpdateRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Builder
    @ConstructorProperties({"email", "name", "password"})
    public UserUpdateRequestData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    @JsonProperty("email")
    public String getUpdateEmail() {
        return email;
    }

    @Override
    @JsonProperty("name")
    public String getUpdateName() {
        return name;
    }

    @Override
    @JsonProperty("password")
    public String getUpdatePassword() {
        return password;
    }
}
