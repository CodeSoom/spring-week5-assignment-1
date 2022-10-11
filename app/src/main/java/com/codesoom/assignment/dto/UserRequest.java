package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {
    @Mapping("email")
    private String email;

    @Mapping("password")
    private String password;
}
