package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        User user = User.builder()
                .id(1L)
                .name("양홍석")
                .email("davidyang2149@gmail.com")
                .password("GuessWhat")
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(2L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .build();
        });
    }

    @Test
    void createUser() {
        UserData userData = UserData.builder()
                .name("양홍석")
                .email("davidyang2149@gmail.com")
                .password("GuessWhat")
                .build();

        User user = userService.createUser(userData);

        verify(userRepository).save(any(User.class));

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("양홍석");
        assertThat(user.getEmail()).isEqualTo("davidyang2149@gmail.com");
        assertThat(user.getPassword()).isEqualTo("GuessWhat");
    }

    @Test
    void updateUserWithExistedId() {
        UserData userData = UserData.builder()
                .name("양홍석")
                .email("davidyang2149@gmail.com")
                .password("GuessWhat")
                .build();

        User user = userService.updateUser(1L, userData);

        assertThat(user.getName()).isEqualTo("양홍석");
        assertThat(user.getEmail()).isEqualTo("davidyang2149@gmail.com");
        assertThat(user.getPassword()).isEqualTo("GuessWhat");
    }

    @Test
    void deleteUserWithExistedId() {
        userService.deleteUser(1L);

        verify(userRepository).delete(any(User.class));
    }
}
