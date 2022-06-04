package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * User 생성 요청 정보
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateData {
    @Mapping("name")
    @NotBlank
    private String name;

    @Mapping("email")
    @NotBlank
    private String email;

    @Mapping("password")
    @NotBlank
    private String password;
}
