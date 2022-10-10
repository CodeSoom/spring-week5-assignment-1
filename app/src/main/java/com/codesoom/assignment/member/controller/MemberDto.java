package com.codesoom.assignment.member.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MemberDto {

    @Getter
    @Setter
    @ToString
    public static class RequestParam {

        @NotBlank
        private String name;

        @NotBlank
        private String password;

        @Email
        @NotNull
        private String email;
    }

}
