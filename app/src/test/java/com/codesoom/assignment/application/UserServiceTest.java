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

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        userService = new UserService(userRepository, mapper);

        User user = User.builder()
                .id(1L)
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        given(userRepository.save(any(User.class)))
                .will(invocation -> {
                    User source = invocation.getArgument(0);

                    return User.builder()
                            .id(1L)
                            .name(source.getName())
                            .email(source.getEmail())
                            .password(source.getPassword())
                            .build();
        });
    }

    @Test
    void createUser() {
        UserData userData = UserData.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        User user = userService.create(userData);

        verify(userRepository).save(any(User.class));

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("김철수");
        assertThat(user.getEmail()).isEqualTo("kim@gmail.com");
        assertThat(user.getPassword()).isEqualTo("1111");
    }

    @Test
    void updateUserWithExistedId() {
        UserData userData = UserData.builder()
                .name("김영희")
                .email("young@gmail.com")
                .password("1234")
                .build();

        User user = userService.update(1L, userData);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("김영희");
    }

    @Test
    void updateUserWithNotExistedId() {
        UserData userData = UserData.builder()
                .name("김영희")
                .email("young@gmail.com")
                .password("1234")
                .build();

        assertThatThrownBy(() -> userService.update(1000L, userData))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUserWithExistedId() {
        userService.delete(1L);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUserWithNotExistedId() {
        assertThatThrownBy(() -> userService.delete(1000L))
                .isInstanceOf(UserNotFoundException.class);
    }
}
