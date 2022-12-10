package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    Long id;

    //이름, 이메일, 비밀번호 필수
    @NotBlank(message = "이름")
    String name;

    @NotBlank(message = "이메일")
    String email;

    @NotBlank(message = "비밀번호")
    String password;
}
