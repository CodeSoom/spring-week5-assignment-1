package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    public UserCreateRequestDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
