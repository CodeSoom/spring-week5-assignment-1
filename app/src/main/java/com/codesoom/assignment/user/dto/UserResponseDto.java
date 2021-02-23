package com.codesoom.assignment.user.dto;

import com.codesoom.assignment.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

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

    public static UserResponseDto of (User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    /**
     *  사용자 응답정보가 동등한 객체라면 true를 리턴하고, 그렇지 않다면 false를 리턴합니다.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserResponseDto)) {
            return false;
        }
        UserResponseDto dto = (UserResponseDto) o;
        return getId().equals(dto.getId());
    }

    /**
     * 사용자 응답정보 객체의 해쉬 정보를 리턴합니다.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
