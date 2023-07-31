package com.codesoom.assignment.dto.user;

import com.codesoom.assignment.domain.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;

    public User toUser() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
