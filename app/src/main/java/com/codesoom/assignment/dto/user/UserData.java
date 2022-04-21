package com.codesoom.assignment.dto.user;

import com.codesoom.assignment.common.Password;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 사용자
 */
public class UserData {

    @Getter
    @Builder
    @ToString
    public static class RegisterUserRequest {

        @NotEmpty
        private final String username;

        @Email(message = "올바른 이메일 형식이 아닙니다")
        private final String email;

        @Password
        private final String password;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateUserRequest {
        @NotEmpty
        private final int id;

        @NotEmpty
        private final String username;

        @Email(message = "올바른 이메일 형식이 아닙니다")
        private final String email;

        @Password
        private final String password;
    }

    @Getter
    @Builder
    @ToString
    public static class RemoveUserRequest {
        @NotEmpty
        private final int id;
    }

}
