package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.codesoom.assignment.domain.UserConstants.USER_DATA;
import static com.codesoom.assignment.domain.UserConstants.USER;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

@Nested
@DisplayName("UserService 클래스")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper dozerMapper;

    OngoingStubbing<User> whenMapper() {
        return when(dozerMapper.map(any(UserData.class), eq(User.class)));
    }

    void verifyMapper() {
        verify(dozerMapper).map(any(UserData.class), eq(User.class));
    }

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        ObjectAssert<User> subject() {
            return assertThat(userService.createUser(USER_DATA));
        }

        OngoingStubbing<User> mockSubject() {
            return when(userRepository.save(any(User.class)));
        }

        @BeforeEach
        void beforeEach() {
            whenMapper()
                .thenReturn(USER);
            mockSubject()
                .thenReturn(USER);
        }

        @AfterEach
        void afterEach() {
            verifyMapper();
            verify(userRepository)
                .save(any(User.class));
        }

        @Test
        @DisplayName("User를 생성하고 리턴한다.")
        void it_creates_a_user() {
            subject()
                .isInstanceOf(User.class);
        }
    }


}
