package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

/**
 * 회원 생성 요청.
 */
@Getter
public class UserCreateRequest {

    @Mapping("name")
    @NotBlank(message = "{user.name.not.blank}")
    private String name;

    @Mapping("email")
    @NotBlank(message = "{user.email.not.blank}")
    private String email;

    @Mapping("password")
    @NotBlank(message = "{user.password.not.blank}")
    private String password;

    @ConstructorProperties({"name", "email", "password"})
    @Builder
    public UserCreateRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
