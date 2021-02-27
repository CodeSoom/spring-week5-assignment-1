package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@ToString
public class UserRequest {
    @Mapping("id")
    private final Long id;

    @NotBlank(message = "name should not be blank")
    @Mapping("name")
    private final String name;

    @NotBlank(message = "email should not be blank")
    @Mapping("email")
    private final String email;

    @NotBlank(message = "password should not be blank")
    @Mapping("password")
    private final String password;
}
