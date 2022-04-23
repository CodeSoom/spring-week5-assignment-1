package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestUserServiceImpl implements UserService {
    @Override
    public User createUser(UserData userData) {
        return User.builder()
                .username(userData.getUsername())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
    }
}
