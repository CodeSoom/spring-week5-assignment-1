package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.users.User;

/**
 * 회원 데이터 전송에 필요한 데이터 정의
 */
public class UserViewDto {

    private final User user;

    private UserViewDto(User user) {
        this.user = user;
    }

    /**
     * 회원을 받아 전송에 필요한 데이터를 리턴합니다.
     *
     * @param user 회원
     */
    public static UserViewDto from(final User user) {
        return new UserViewDto(user);
    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getName() {
        return user.getName();
    }
}
