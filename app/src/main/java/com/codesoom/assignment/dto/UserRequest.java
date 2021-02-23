package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotBlank
    private String sex;
}
