package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    @InjectMocks
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Test
    void createUser(){
        User user = User.builder()
                .email("wenodev@codesoo.com")
                .name("weno")
                .password("pw1234")
                .build();

        given(userService.createUser()).willReturn(List.of(User));

    }

}