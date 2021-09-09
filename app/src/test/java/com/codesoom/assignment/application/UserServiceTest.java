package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
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
    void setUpAll() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);

        given(userRepository.save(any(User.class))).will(invocation -> {
            User user = invocation.getArgument(0);
            return User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
        });
    }



    @Test
    void createUser() {
        //Arrange
        String name = "name";
        String email = "email";
        String password = "password";
        UserData source = UserData.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        //Act
        UserData userData = userService.createUser(source);

        verify(userRepository).save(any(User.class));

        assertThat(userData.getName()).isEqualTo(name);
        assertThat(userData.getEmail()).isEqualTo(email);
        assertThat(userData.getPassword()).isEqualTo(password);
    }
}
