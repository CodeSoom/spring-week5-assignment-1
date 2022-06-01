package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserData {
    private Long id;

    @Mapping("name")
    @NotBlank(message = "name 값은 필수입니다")
    private String name;

    @Mapping("email")
    @NotBlank(message = "email 값은 필수입니다")
    private String email;

    @Mapping("password")
    @NotBlank(message = "password 값은 필수입니다")
    private String password;

    @Builder
    public UserData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
