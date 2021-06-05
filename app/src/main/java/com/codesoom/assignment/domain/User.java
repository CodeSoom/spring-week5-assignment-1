package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 유저 엔티티.
 */
@Entity
@Builder
@Getter
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
     * 유저를 주어진 유저 정보로 갱신합니다.
     *
     * @param source 주어진 유저 정보
     */
    public void change(User source) {
        this.name = source.getName();
        this.email = source.getEmail();
        this.password = source.getPassword();
    }
}
