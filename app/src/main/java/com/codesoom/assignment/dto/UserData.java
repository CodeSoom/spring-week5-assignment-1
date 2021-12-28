package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("email")
    private String email;

    @Mapping("password")
    private String password;
}
