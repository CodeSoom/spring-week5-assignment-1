package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/*
1. getAllUsers : 완료
2. getUser
3. createUser
4. updateUser
5. deleteUser
 */
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers(){
        User user = User.builder()
                .email("wenodev@codesoom.com")
                .name("weno")
                .password("pwd1234")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user);

        given(userRepository.findAll()).willReturn(users);

        assertThat(users.get(0).getName()).isEqualTo("weno");
    }

    @Test
    void getUserWithExistedUser(){
        User user = User.builder()
                .email("wenodev@codesoom.com")
                .name("weno")
                .password("pwd1234")
                .build();

        assertThat(userService.getUser())

    }

}