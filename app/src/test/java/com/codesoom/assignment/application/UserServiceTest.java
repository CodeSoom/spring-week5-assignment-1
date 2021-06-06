package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
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

    UserData updateUser;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        User user = User.builder()
                .id(1L)
                .name("KIM")
                .email("code@soom.com")
                .password("123456")
                .age(22).build();

        updateUser = UserData.builder()
                .name("LIM")
                .email("java@spring.com")
                .build();

        given(userRepository.save(any(User.class))).will(invocation -> {
            User source = invocation.getArgument(0);
            return User.builder()
                    .id(2L)
                    .name(source.getName())
                    .email(source.getEmail())
                    .password(source.getPassword())
                    .age(source.getAge())
                    .build();
        });


        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.findById(1000L)).willThrow(UserNotFoundException.class);
    }

    @Test
    void createUser() {
        UserData userData = UserData.builder()
                .name("Kim")
                .email("123@google.com")
                .password("3000")
                .build();

        User user = userService.createUser(userData);

        assertThat(user.getName()).isEqualTo(userData.getName());
    }

    @Test
    void updateUserWithExistedId() {

        User user = userService.updateUser(1L, updateUser);

        assertThat(user.getName()).isEqualTo(updateUser.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void updateUserWithNotExistedId() {

        assertThatThrownBy(() -> userService.updateUser(1000L, updateUser))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUserWithExistedId() {
        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.deleteUser(1000L))
                .isInstanceOf(UserNotFoundException.class);
    }
}
