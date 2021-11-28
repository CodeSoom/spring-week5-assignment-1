package com.codesoom.assignment.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class RegisterData {
    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
