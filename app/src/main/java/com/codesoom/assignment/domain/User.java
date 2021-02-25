package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 사용자 정보를 다룬다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "id")
public class User {
    /** 사용자 식별자 */
    @Id
    @GeneratedValue
    private Long id;

    /** 사용자 이름 */
    private String name;

    /** 사용자 이메일 */
    private String email;

    /** 사용자 비밀번호 */
    private String password;

    @Builder
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /** 사용자 정보를 업데이트한다. */
    public void update(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
