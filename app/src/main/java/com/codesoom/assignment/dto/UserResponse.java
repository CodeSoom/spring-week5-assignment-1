package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class UserResponse {
    @JsonProperty
    private Long id;

    @NotBlank
    @JsonProperty
    private String name;

    @NotBlank
    @JsonProperty
    private String email;

    @NotBlank
    @JsonProperty
    private String password;

    @Builder
    public UserResponse(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
