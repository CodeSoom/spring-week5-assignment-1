package com.codesoom.assignment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
public class CreateUserDto {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    String name;
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    String email;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    String password;
}
