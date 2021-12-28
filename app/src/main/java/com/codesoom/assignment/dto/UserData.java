package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;
    @NotBlank
    @Mapping("name")
    private String name;
    @NotBlank
    @Mapping("password")
    private String password;
    @NotBlank
    @Mapping("email")
    private String email;
}
