package com.codesoom.assignment.member.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @lombok.Generated
    @Getter
    @Setter
    @ToString
    public static class RequestParam {

        @NotBlank(message = "이름은 필수항목 입니다.")
        private String name;

        @NotBlank(message = "패스워드는 필수항목 입니다.")
        private String password;

        @NotBlank(message = "이메일은 필수항목 입니다.")
        @Email(message = "이메일 형식에 맞게 입력해주세요.")
        private String email;
    }

}
