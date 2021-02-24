package com.codesoom.assignment.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 사용자 정보.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    /**
     * 사용자 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 이름.
     */
    @NotNull
    private String name;

    /**
     * 사용자 이메일.
     */
    @Column(unique = true)
    @NotNull
    private String email;

    /**
     * 사용자 비밀번호.
     */
    @NotNull
    private String password;

    @Builder
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 사용자의 정보를 갱신합니다.
     * @param source 사용자 갱신 정보
     */
    public void changeWith(User source) {
        this.email = source.getEmail();
        this.name = source.getName();
        this.password = source.getPassword();
    }
}
