package com.codesoom.assignment.dto;

import com.codesoom.assignment.global.PassWordForm;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @Mapping("id")
    private int id;
    @Mapping("name")
    @NotBlank
    private String name;
    @NotBlank
    @Mapping("email")
    private String email;
    @PassWordForm
    @Mapping("password")
    private String password;
}
