package com.codesoom.assignment.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @Builder
    private User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 회원을 수정하고 수정된 회원을 리턴합니다.
     *
     * @param source 수정하고자 하는 회원
     * @return 수정된 회원
     */
    public User changeWith(User source) {
        this.name = source.name;
        this.password = source.password;

        return this;
    }

}
