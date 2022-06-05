package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateData {
    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    @Override
    public String toString() {
        return "UserCreateData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
