package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRegistrationData {
    @NotBlank
    @Size(min = 3)
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 4 , max = 1024)
    private String password;
}
