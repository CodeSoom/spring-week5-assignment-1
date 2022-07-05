package com.codesoom.assignment.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("UserService 클래스")
public class UserServiceTest {
    private UserService service;

    @BeforeEach
    void setUp() {
        this.service = new UserService();
    }

    
}
