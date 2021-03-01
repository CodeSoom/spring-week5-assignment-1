package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;



@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserData {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    @Builder
    public UserData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
