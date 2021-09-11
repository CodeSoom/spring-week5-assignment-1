package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import lombok.Getter;

@Getter
public class CreateUserResponseDto {

    private final String name;
    private final String email;

    public CreateUserResponseDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
