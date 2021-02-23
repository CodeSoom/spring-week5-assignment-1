package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/*
1. getAllUsers : 완료
2. getUser
3. createUser
4. updateUser
5. deleteUser
 */
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp(){
        userService = new UserService();
    }

    @Test
    void getAllUsers(){
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
    }
}