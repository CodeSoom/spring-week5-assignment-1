package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 사용자 Dto.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @NotBlank(message = "이름을 입력 해주세요")
    @Mapping("name")
    private String name;

    @Email(message = "이메일을 입력 해주세요")
    @NotBlank
    @Mapping("email")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요")
    @Mapping("password")
    private String password;
}
