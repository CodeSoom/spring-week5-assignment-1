package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
