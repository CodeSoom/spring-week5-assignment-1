package com.codesoom.assignment.dto;

import com.codesoom.assignment.controllers.NullOrNoEmptyString;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    @NullOrNoEmptyString
    @Mapping("email")
    private String email;

    @NullOrNoEmptyString
    @Mapping("name")
    private String name;

    @NullOrNoEmptyString
    @Mapping("password")
    private String password;
}
