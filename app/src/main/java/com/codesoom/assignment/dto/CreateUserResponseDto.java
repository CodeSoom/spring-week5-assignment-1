package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
public class CreateUserResponseDto {

    @JsonInclude(Include.NON_NULL)
    private final Long id;
    private final String name;
    private final String email;

    public CreateUserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
