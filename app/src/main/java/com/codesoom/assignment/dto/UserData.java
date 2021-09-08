package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;

import com.github.dozermapper.core.Mapping;

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
public class UserData {
    private Long id;

    @NotBlank(message = "이름을 입력하세요.")
    @Mapping("name")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    @Mapping("email")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Mapping("password")
    private String password;
}
