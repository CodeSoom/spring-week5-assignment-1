package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Email
    @Mapping("email")
    private String email;

    @NotBlank
    @Pattern(regexp="[a-zA-z1-9]{6,12}")
    @Mapping("password")
    private String password;

    @Mapping("age")
    private int age;
}
