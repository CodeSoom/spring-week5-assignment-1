package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class UpdateUserData {
    public UpdateUserData(
            @JsonProperty("name") String name,
            @JsonProperty("password") String password
    ) {
        this.name = name;
        this.password = password;
    }

    @Getter
    @NotBlank
    private String name;
    @Getter
    @NotBlank
    private String password;
}
