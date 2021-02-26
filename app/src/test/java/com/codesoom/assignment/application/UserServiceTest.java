package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private static final String NAME = "양효주";
    private static final String EMAIL = "yhyojoo@codesoom.com";
    private static final String PASSWORD = "112233!!";

    private UserService userService;

    private UserRepository userRepository = mock(UserRepository.class);

    private final Mapper dozerMapper = DozerBeanMapperBuilder.buildDefault();

    @BeforeEach
    void setUp() {
        userService = new UserService(dozerMapper, userRepository);

        given(userRepository.save(any(User.class)))
                .will(invocation -> invocation.<Product>getArgument(0));
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        UserData userData;

        @Test
        @DisplayName("새로운 사용자를 등록한다")
        void it_returns_user() {
            userData = new UserData();

            userService.createUser(userData);

            verify(userRepository).save(any(User.class));
        }
    }
}
