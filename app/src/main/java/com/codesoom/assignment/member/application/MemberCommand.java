package com.codesoom.assignment.member.application;

import com.codesoom.assignment.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberCommand {
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
