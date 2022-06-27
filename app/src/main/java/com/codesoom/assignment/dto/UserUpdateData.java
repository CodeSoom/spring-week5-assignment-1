package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateData {
    @NotBlank
    @Mapping("name")
    private String name;

    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    @Override
    public String toString() {
        return "UserUpdateData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
