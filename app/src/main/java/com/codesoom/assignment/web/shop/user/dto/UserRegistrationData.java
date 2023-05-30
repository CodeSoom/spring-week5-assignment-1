package com.codesoom.assignment.web.shop.user.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class UserRegistrationData {
    @NotBlank
    @Size(min = 3)
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("name")
    private String name;
    @NotBlank
    @Size(min = 4)
    @Mapping("password")
    private String password;
}
