package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;

    @NotBlank
    @Setter
    @Mapping("name")
    private String name;

    @NotBlank
    @Setter
    @Mapping("email")
    private String email;

    @NotBlank
    @Setter
    @Mapping("password")
    private String password;

}
