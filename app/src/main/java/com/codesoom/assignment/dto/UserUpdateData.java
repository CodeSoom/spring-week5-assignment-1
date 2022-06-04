package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * User 수정 요청 정보
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateData {
    @Mapping("name")
    @NotEmpty
    private String name;

    @Mapping("email")
    private String email;

    @Mapping("password")
    @NotEmpty
    private String password;
}
