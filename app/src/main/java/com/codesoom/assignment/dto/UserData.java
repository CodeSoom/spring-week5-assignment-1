package com.codesoom.assignment.dto;


import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
public class UserData {

    UserData(){ }

    UserData(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

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
