package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원.
 */
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String password;

    protected User() {
    }

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 회원 정보를 수정하고, 리턴합니다.
     *
     * @param source 수정할 회원 정보
     * @return 수정된 회원
     */
    public User changeInfo(User source) {
        this.name = source.getName();
        this.email = source.getEmail();
        this.password = source.getPassword();

        return this;
    }
}
