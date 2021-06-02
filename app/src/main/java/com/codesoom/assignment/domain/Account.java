package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 회원정보 Entity입니다.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    /**
     * 회원정보 고유 ID값 입니다.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 회원의 이름입니다.
     * 값은 Null이거나 공백일 수 없습니다.
     */
    private String name;

    /**
     * 회원의 비밀번호입니다.
     * 값은 Null이거나 공백일 수 없습니다.
     */
    private String password;

    /**
     * 회원의 이메일입니다.
     * 값은 Null이거나 공백일 수 없습니다.
     */
    private String email;

    /**
     * 회원정보를 변경할 때 사용합니다.
     *
     * @param source 변경할 회원정보의 내용입니다.
     */
    public void changeAccData(Account source) {
        this.email = source.getEmail();
        this.name = source.getName();
        this.password = source.getPassword();
    }
}
