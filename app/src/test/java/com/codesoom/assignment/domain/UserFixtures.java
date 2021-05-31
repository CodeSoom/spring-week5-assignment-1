package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;

public class UserFixtures {
    public static User alice() {
        return User.builder()
                   .id(1L)
                   .name("Alice")
                   .email("alice@codesoom.com")
                   .password("pw-alice")
                   .build();
    }

    public static UserData validAliceData() {
        return UserData.builder()
                       .name("Alice")
                       .email("alice@codesoom.com")
                       .password("pw-alice")
                       .build();
    }

    public static UserData invalidAliceData() {
        return UserData.builder()
                       .name("Alice")
                       .password("pw-alice")
                       .build();
    }

    public static User bob() {
        return User.builder()
                   .id(2L)
                   .name("Bob")
                   .password("pw-bob")
                   .build();
    }
}
