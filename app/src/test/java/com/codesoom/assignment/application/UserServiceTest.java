package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                .name("johndoe")
                .email("johndoe@gmail.com")
                .password("verysecret")
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
                .name("johndoe")
                .email("johndoe@gmail.com")
                .password("verysecret")
                .build();

        User user = userService.createUser(userData);

        verify(userRepository).save(any(User.class));

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("johndoe");
        assertThat(user.getEmail()).isEqualTo("johndoe@gmail.com");
        assertThat(user.getPassword()).isEqualTo("verysecret");
    }

    @Test
    void updateUserWithExistedId() {
        UserData userData = UserData.builder()
                .name("janedoe")
                .email("janedoe@gmail.com")
                .password("verysecret!")
                .build();

        User user = userService.updateUser(1L, userData);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("janedoe");
        assertThat(user.getEmail()).isEqualTo("janedoe@gmail.com");
        assertThat(user.getPassword()).isEqualTo("verysecret!");
    }

    @Test
    void updateUserWithNotExistedId() {
        UserData userData = UserData.builder()
                .name("janedoe")
                .email("janedoe@gmail.com")
                .password("verysecret!")
                .build();

        assertThatThrownBy(() -> userService.updateUser(1000L, userData))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUserWithExistedId() {
        userService.deleteUser(1L);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.deleteUser(1000L))
                .isInstanceOf(UserNotFoundException.class);
    }
}
