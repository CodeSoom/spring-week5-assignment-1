package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.dozermapper.core.Mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("name")
    private String email;

    @NotBlank
    @Mapping("name")
    private String password;
}
