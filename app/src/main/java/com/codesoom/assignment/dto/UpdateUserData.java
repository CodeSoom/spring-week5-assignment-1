package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;

import com.github.dozermapper.core.Mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserData {
    @NotBlank(message = "이름이 입력되지 않았습니다.")
    @Mapping("name")
    private String name;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Mapping("password")
    private String password;
}
