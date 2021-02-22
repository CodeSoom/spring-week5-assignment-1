package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp(){
        user = new User("name", "email", "password");
    }

    @Test
    void test_변수값확인(){
        assertThat("name").isEqualTo(user.getName());
    }

}