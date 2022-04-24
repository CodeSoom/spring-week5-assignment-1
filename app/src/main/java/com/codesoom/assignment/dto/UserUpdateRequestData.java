package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.users.UserUpdateRequest;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

/**
 * 회원 수정에 필요한 데이터
 */
public class UserUpdateRequestData implements UserUpdateRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String name;

    @NotBlank
    private final String password;

    @Builder
    @ConstructorProperties({"email", "name", "password"})
    public UserUpdateRequestData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
