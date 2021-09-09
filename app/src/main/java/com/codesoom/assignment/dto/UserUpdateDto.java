package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 수정정보를 표현합니다
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("password")
    private String password;
}
