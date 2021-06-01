package com.codesoom.assignment.domain;

// 회원 Entity
// 이름, string, not null
// 이메일, string, not null, email validation
// 비밀번호, string, not null
// 유효성 검사

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue
    private Long id;

    @Email(message = "이메일 형식을 맞춰주세요")
    private String email;

    private String name;

    private int age;

    @Pattern(regexp="[a-zA-z1-9]{6,12}")
    private String password;

    public void changeWith(User source) {
        this.name = source.name;
        this.email = source.email;
        this.password = source.password;
        this.age = source.age;
    }
}
