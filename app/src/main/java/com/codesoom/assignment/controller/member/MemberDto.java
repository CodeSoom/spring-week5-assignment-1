package com.codesoom.assignment.controller.member;

import com.codesoom.assignment.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@lombok.Generated
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

    @lombok.Generated
    @Getter
    @Setter
    @ToString
    public static class UpdateParam {

        @NotBlank(message = "이름은 필수항목 입니다.")
        private String name;

        @NotBlank(message = "패스워드는 필수항목 입니다.")
        private String password;

        private String email;

    }

    @lombok.Generated
    @Getter
    @ToString
    public static class MemberInfo {
        private final Long id;

        private final String name;

        private final String password;

        private final String email;

        public MemberInfo(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.password = member.getPassword();
            this.email = member.getEmail();
        }
    }
}
