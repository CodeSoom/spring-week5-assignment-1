package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 회원 정보 수정용 DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class UserModificationData {
    @NotBlank(message = "이름")
    private String name;

    @Pattern(regexp = "[a-zA-Z0-9]{6,12}", message = "비밀번호")
    private String password;
}
