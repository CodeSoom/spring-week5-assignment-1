package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
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

    private final UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);

        User user = User.builder()
                .id(1L)
                .name("caoyu")
                .email("choyumin01@gmail.com")
                .password("1234!@#$")
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
        UserCreateData userCreateData = UserCreateData.builder()
                .name("caoyu-dev")
                .email("choyumin01@gmail.com")
                .password("!@#$1234")
                .build();

        User user = userService.createUser(userCreateData);

        verify(userRepository).save(any(User.class));

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("caoyu-dev");
        assertThat(user.getEmail()).isEqualTo("choyumin01@gmail.com");
        assertThat(user.getPassword()).isEqualTo("!@#$1234");
    }

    @Test
    void updateUserWithExistedId() {
        UserUpdateData userUpdateData = UserUpdateData.builder()
                .name("meow")
                .email("choyumin01@gmail.com")
                .password("meow1234")
                .build();

        User user = userService.updateUser(1L, userUpdateData);

        assertThat(user.getName()).isEqualTo("meow");
        assertThat(user.getEmail()).isEqualTo("choyumin01@gmail.com");
        assertThat(user.getPassword()).isEqualTo("meow1234");
    }

    @Test
    void updateUserWithNotExistedId() {
        UserUpdateData userUpdateData = UserUpdateData.builder()
                .name("meow")
                .email("choyumin01@gmail.com")
                .password("meow1234")
                .build();

        assertThatThrownBy(() -> userService.updateUser(1000L, userUpdateData))
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