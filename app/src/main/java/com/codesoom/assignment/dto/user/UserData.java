package com.codesoom.assignment.dto.user;

import com.codesoom.assignment.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public User toUser() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
