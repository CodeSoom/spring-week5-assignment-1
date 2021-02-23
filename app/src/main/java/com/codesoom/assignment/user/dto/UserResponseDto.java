package com.codesoom.assignment.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Builder
    public UserResponseDto(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
