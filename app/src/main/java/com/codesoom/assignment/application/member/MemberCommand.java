package com.codesoom.assignment.application.member;

import com.codesoom.assignment.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@lombok.Generated
public class MemberCommand {

    @lombok.Generated
    @Getter
    @Builder
    @ToString
    public static class Register {
        private final String name;

        private final String password;

        private final String email;

        public Member toEntity() {
            return Member.builder()
                    .name(name)
                    .password(password)
                    .email(email)
                    .build();
        }

    }

    @lombok.Generated
    @Getter
    @Builder
    @ToString
    public static class UpdateRequest {
        private final Long id;

        private final String name;

        private final String password;

        private final String email;

        public Member toEntity() {
            return Member.builder()
                    .id(id)
                    .name(name)
                    .password(password)
                    .email(email)
                    .build();
        }
    }
}
