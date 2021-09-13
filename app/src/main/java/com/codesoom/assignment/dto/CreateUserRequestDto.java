package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원 생성 요청 시에 사용합니다.
 */
@Getter
public class CreateUserRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    protected CreateUserRequestDto() {
    }

    @Builder
    public CreateUserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 엔티티로 변환하여 리턴합니다.
     *
     * @return 변환된 엔티티
     */
    public User toEntity() {
        return User.builder()
            .name(name)
            .email(email)
            .password(password)
            .build();
    }
}

