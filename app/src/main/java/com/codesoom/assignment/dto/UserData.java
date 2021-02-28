package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;

    @NotBlank
    @Setter
    @Getter
    @Mapping("name")
    private String name;

    @NotBlank
    @Setter
    @Getter
    @Mapping("email")
    private String email;

    @NotBlank
    @Setter
    @Getter
    @Mapping("password")
    private String password;

}
