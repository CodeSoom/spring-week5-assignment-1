package com.codesoom.assignment.user.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 사용자 갱신요청 정보.
 */
@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    /**
     * 사용자 이름.
     */
    @NotBlank(message = "이름은 필수값입니다.")
    @Mapping("name")
    private String name;

    /**
     * 사용자 이메일.
     */
    @Email
    @Mapping("email")
    private String email;

    /**
     * 사용자 비밀번호.
     */
    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Mapping
    private String password;

    /**
     * 사용자 갱신정보 생성자
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     */
    @Builder
    public UserUpdateRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
