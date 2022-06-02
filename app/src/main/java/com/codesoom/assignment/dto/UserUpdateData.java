package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String password;
}
