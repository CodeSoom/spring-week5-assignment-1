package com.codesoom.assignment.dto;


import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class UserData {

    public UserData(){ }

    UserData(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Mapping("id")
    private Long id;

    @Mapping("name")
    @NotBlank
    private String name;

    @Mapping("email")
    @NotBlank
    private String email;

    @Mapping("password")
    @NotBlank
    private String password;
}
