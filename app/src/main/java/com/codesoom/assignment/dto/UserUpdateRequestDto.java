package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
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
public class UserUpdateRequestDto {

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;

    public UserUpdateRequestDto(User user) {
        this.name = user.getName();
        this.password = user.getPassword();
    }
}
