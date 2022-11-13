package com.codesoom.assignment.user.adapter.in.web.dto.request;

import com.codesoom.assignment.user.application.in.command.UserCreateRequest;
import com.codesoom.assignment.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserCreateRequestDto implements UserCreateRequest {

    @NotBlank(message = "이름을 입력하세요")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
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
