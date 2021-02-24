package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Builder
    public UpdateUserRequest(@NotBlank String name,
                             @NotBlank String password) {
        this.name = name;
        this.password = password;
    }
}
