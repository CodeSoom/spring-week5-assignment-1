package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.checkerframework.checker.units.UnitsTools.min;

@NotBlank
public class RegisterData {
    @Size(min = 8)
    private String password;

    private String name;

    private String email;
}
