package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 결과 리턴용 DTO
 */
@Builder
@Getter
@AllArgsConstructor
public class UserResultData {
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;
}
