package com.codesoom.assignment.domain;

import lombok.Getter;

import javax.persistence.Id;

@Getter
public class User {

    /**
     * TODO: User 객체 생성
     * 이름, 이메일 ,비밀번호 (필수입력조건)
     *
     * UserID, Name, e-mail, password
     */

    @Id
    Long Id;
    String name;
    String email;
    String password;

    public User(){

    }

    public User(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public User(String name, String email, String pwd){
        this.name = name;
        this.email = email;
        this.password = pwd;
    }

}
