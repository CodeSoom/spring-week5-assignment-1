package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Builder
@ToString
public class UserRequest {
    @JsonProperty
    @Mapping("id")
    private Long id;

    @NotBlank
    @JsonProperty
    @Mapping("name")
    private String name;

    @NotBlank
    @JsonProperty
    @Mapping("email")
    private String email;

    @NotBlank
    @JsonProperty
    @Mapping("password")
    private String password;
}
