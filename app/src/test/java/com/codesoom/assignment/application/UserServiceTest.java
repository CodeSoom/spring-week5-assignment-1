package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/*
1. getAllUsers
2. getUser
3. createUser
4. updateUser
5. deleteUser
 */
class UserServiceTest {

    private UserService userService;

    @Test
    void getAllUsers(){
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
    }

}