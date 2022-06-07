package com.codesoom.assignment.controllers.dtos;

import com.codesoom.assignment.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseData {
    private Long id;

    private String name;

    private String email;

    private String password;


    public static UserResponseData from(User user) {
        return UserResponseData.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
