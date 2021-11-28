package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * TODO
 * 이름
 * 이메일
 * 비밀번호
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberData {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
