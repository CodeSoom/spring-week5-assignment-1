package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Builder
@ToString
public class UserRequest {
    @Mapping("id")
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;
}
