package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 사용자 Dto.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @NotBlank
    @Mapping("name")
    private String name;

    @Email
    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;
}
