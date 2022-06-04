package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * 회원 정보
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    /**
     * 회원 정보 업데이트
     *
     * @param name     회원명
     * @param email    회원메일
     * @param password 회원패스워크
     */
    public void update(String name,
                       String email,
                       String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
