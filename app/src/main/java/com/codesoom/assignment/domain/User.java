package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 사용자 정보를 저장하고 처리합니다.
 */
@Entity
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;

    private String password;

    public User() {}

    public User(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    /**
     * 사용자 정보를 변경합니다.
     * @param name 사용자 이름
     * @param password 비밀번호
     */
    public void update(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
