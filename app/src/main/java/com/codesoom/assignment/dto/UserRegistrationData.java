package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 회원가입용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationData {
    //이름, 이메일, 비밀번호 필수
    @NotBlank(message = "이름")
    String name;

    @Email(message = "이메일")
    String email;

    @Pattern(regexp = "[a-zA-Z0-9]{6,12}", message = "비밀번호")
    String password;
}
