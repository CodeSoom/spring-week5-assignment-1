package com.codesoom.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class UserResponseDto {
    @NotBlank
    private String name;

    @NotBlank
    @Id
    private String email;

    @NotBlank
    private Long password;

    public UserResponseDto(String name, String email, Long password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserResponseDto() {

    }
}
