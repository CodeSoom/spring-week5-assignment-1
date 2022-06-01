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
public class UserUpdateData {
    @Mapping("name")
    @NotBlank
    private String name;

    @Mapping("email")
    @NotBlank
    private String email;

    @Mapping("password")
    @NotNull
    private String password;
}
