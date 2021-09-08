package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private String email;
}
