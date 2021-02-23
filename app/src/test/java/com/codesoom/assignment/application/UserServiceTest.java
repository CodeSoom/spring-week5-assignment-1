package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Test
    void createUser(){
        User user = User.build();
    }

}