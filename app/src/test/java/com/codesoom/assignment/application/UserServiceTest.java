package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp(){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        User user = User.builder()
                .id(1L)
                .name("Kim")
                .email("123@google.com")
                .password("3000")
                .age(21)
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
    }

    @Test
    void createUser(){
        UserData userData = UserData.builder()
                .name("Kim")
                .email("123@google.com")
                .password("3000")
                .build();

        User user = userService.createUser(userData);
        assertThat(user.getName()).isEqualTo("Kim");
    }
}
