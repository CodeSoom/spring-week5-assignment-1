package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.UserRepository;

import static org.mockito.Mockito.mock;

class UserServiceTest {
    private static final String NAME = "양효주";
    private static final String EMAIL = "yhyojoo@codesoom.com";
    private static final String PASSWORD = "112233!!";

    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);
}
