package com.codesoom.assignment.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    /**
     * 사용자 식별자.
     */
    private Long id;

    /**
     * 사용자 이름.
     */
    private String name;

    /**
     * 사용자 이메일.
     */
    private String email;

    /**
     * 사용자 비밀번호.
     */
    @JsonIgnore
    private String password;

    /**
     * 사용자정보 응답 생성자
     * @param id 사용자 식별자
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     */
    @Builder
    public UserResponseDto(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
