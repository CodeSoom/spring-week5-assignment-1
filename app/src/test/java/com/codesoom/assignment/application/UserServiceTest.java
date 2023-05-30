package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.web.shop.user.dto.UserRegistrationData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {


    private UserRepository userRepository = mock(UserRepository.class);
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private UserService userService = new UserService(userRepository, mapper);

    @Test
    void create() {
        UserRegistrationData registrationData = UserRegistrationData.builder()
                .name("test")
                .password("password")
                .email("test@naver.com")
                .build();
        User build = User.builder().name(registrationData.getName())
                .id(13L)
                .password(registrationData.getPassword())
                .email(registrationData.getEmail())
                .build();

        given(userRepository.save(any())).willReturn(build);
        User user = userService.registerUser(registrationData);
        Assertions.assertThat(user.getEmail()).isEqualTo(registrationData.getEmail());
    }
}
