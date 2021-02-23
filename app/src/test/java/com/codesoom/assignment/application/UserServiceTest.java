package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.application.dto.UserReqestDto;
import com.codesoom.assignment.application.dto.UserRequestDto;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/*
1. getAllUsers : 완료
2. getUser : 완료
3. createUser
4. updateUser
5. deleteUser
 */
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);
    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        User user = User.builder()
                .id(1L)
                .name("weno")
                .password("weno@codesoom.com")
                .password("pwd111")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.findById(1000L)).willThrow(new UserNotFoundException(1L));

    }

    @Test
    void getAllUsers() {
        List<User> users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getName()).isEqualTo("weno");
    }

    @Test
    void getUserWithExistedId(){
        User user = userService.getUser(1L);
        assertThat(user.getName()).isEqualTo("weno");
    }

    @Test
    void getUserWithNotExistedId(){
        assertThatThrownBy(()-> userService.getUser(1000L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createUser(){
        UserRequestDto userRequestDto;
    }

}
