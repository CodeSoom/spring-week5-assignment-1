package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter //getter 없으면 ServletResponse에서 응답값 매핑이 안되어 응답값이 빈값으로 내려감
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    public void change(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
