package com.codesoom.assignment.application.member;

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

    }
}
