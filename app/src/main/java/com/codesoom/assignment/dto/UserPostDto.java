package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 사용자 생성 정보를 표현합니다
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPostDto {
    @Email
    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;
}
