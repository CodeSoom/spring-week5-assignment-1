package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    @Mapping("name")
    @NotBlank
    private String name;

    @Mapping("password")
    @NotBlank
    private String password;

    @Builder
    public UpdateUserRequest(@NotBlank String name,
                             @NotBlank String password) {
        this.name = name;
        this.password = password;
    }
}
