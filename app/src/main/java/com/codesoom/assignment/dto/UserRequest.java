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

    @NotBlank
    @Mapping("name")
    private final String name;

    @NotBlank
    @Mapping("email")
    private final String email;

    @NotBlank
    @Mapping("password")
    private final String password;
}
