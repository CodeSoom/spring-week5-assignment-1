package com.codesoom.assignment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter(AccessLevel.PROTECTED)
public class UpdateUserDto {
    @NotEmpty
    String name;
    String email;
    @NotEmpty
    String password;
}
