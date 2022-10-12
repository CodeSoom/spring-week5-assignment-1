package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class UserRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private Long password;

    public UserRequestDto(String name, String email, Long password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRequestDto() {
    }
}
