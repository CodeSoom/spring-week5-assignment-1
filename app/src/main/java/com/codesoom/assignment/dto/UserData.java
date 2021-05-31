package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 데이터.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Mapping("name")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Mapping("email")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Mapping("password")
    private String password;
}
