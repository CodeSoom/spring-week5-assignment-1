package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserData {
    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotNull
    @Mapping("password")
    private String password;
}
