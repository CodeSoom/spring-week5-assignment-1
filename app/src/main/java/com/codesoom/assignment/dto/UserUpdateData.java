package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateData {
    @Mapping("name")
    @NotEmpty
    private String name;

    @Mapping("email")
    private String email;

    @Mapping("password")
    @NotEmpty
    private String password;
}
