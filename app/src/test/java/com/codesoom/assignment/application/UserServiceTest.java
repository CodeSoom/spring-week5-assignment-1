package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private Mapper mapper;

    private UserRequest userRequest;

    private static final Long USER_ID = 1L;
    private static final String NAME = "Min";
    private static final String EMAIL = "min@gmail.com";
    private static final String PASSWORD = "1q2w3e!";

    @BeforeEach
    void setUp() {
        mapper = DozerBeanMapperBuilder.buildDefault();
        userRepository = mock(UserRepository.class);
        userService = new UserService(mapper, userRepository);

        userRequest = UserRequest.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Test
    @DisplayName("새로운 사용자를 생성한다")
    void createUser() {
        given(userRepository.save(any(User.class)))
                .will(invocation -> invocation.<User>getArgument(0));

        userService.createUser(userRequest);

        verify(userRepository).save(any(User.class));
    }
}
