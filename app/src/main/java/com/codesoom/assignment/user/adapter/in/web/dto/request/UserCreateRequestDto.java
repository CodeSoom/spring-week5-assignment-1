package com.codesoom.assignment.user.adapter.in.web.dto.request;

import com.codesoom.assignment.user.application.in.command.UserCreateRequest;
import com.codesoom.assignment.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UserCreateRequestDto implements UserCreateRequest {

    @NotBlank(message = "이름을 입력하세요")
    private String name;

    // Regular Expression by RFC 5322 for Email Validation
    // Ref: https://www.baeldung.com/java-email-validation-regex#regular-expression-by-rfc-5322-for-email-validation
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "올바른 이메일 형식으로 입력하세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    @Builder
    public UserCreateRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
