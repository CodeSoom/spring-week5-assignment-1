package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
