package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue
    Long id;

    //이름, 이메일, 비밀번호 필수
    @NotBlank(message = "이름")
    String name;

    @NotBlank(message = "이메일")
    String email;

    @NotBlank(message = "비밀번호")
    String password;
}
