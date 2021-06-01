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
import java.io.Serializable;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Email(message = "이메일 형식을 맞춰주세요")
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp="[a-zA-z1-9]{6,12}")
    private String password;
}
