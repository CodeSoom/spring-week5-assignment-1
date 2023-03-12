package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCreateDto {

    private Long id;

    @NotBlank(message = "이름을 입력하세요")
    private String name;

    @NotBlank(message = "이메일을 입력하세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    public User toEntity(){
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
