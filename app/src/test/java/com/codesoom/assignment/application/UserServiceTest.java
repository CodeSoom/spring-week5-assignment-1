package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.codesoom.assignment.exceptions.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private Mapper mapper;

    private UserRequest userRequest;
    private UserResponse userResponse;

    private static final Long USER_ID = 1L;
    private static final String NAME = "Min";
    private static final String NEW_NAME = "New Min";
    private static final String EMAIL = "min@gmail.com";
    private static final String NEW_EMAIL = "new_min@gmail.com";
    private static final String PASSWORD = "1q2w3e!";
    private static final String NEW_PASSWORD = "new_1q2w3e!";

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

        userResponse = UserResponse.builder()
                        .id(USER_ID)
                        .name(NEW_NAME)
                        .email(NEW_EMAIL)
                        .password(NEW_PASSWORD)
                        .build();

        User user = User.builder()
                .id(USER_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();




        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
    }

    @Test
    @DisplayName("새로운 사용자를 생성한다")
    void createUser() {
        given(userRepository.save(any(User.class)))
                .will(invocation -> invocation.<User>getArgument(0));

        userService.createUser(userRequest);

        verify(userRepository).save(any(User.class));
    }

    @Nested
    @DisplayName("updateUser 는")
    class Context_updateUser {
        @Nested
        @DisplayName("존재하는 id로 사용자 정보를 수정할 경우")
        class Describe_with_existing_user_id {
            @BeforeEach
            void setUp() {
                given(userRepository.save(any(User.class))).will(invocation -> {
                            User source = invocation.getArgument(0);
                            return User.builder()
                                    .id(USER_ID)
                                    .name(source.getName())
                                    .email(source.getEmail())
                                    .password(source.getPassword())
                                    .build();
               });
            }

            @DisplayName("해당 id의 사용자 정보를 업데이트 한다")
            @Test
            void it_updates_user_detail() {

                UserRequest userRequest = UserRequest.builder()
                        .id(USER_ID)
                        .name(NEW_NAME)
                        .email(NEW_EMAIL)
                        .password(NEW_PASSWORD)
                        .build();

                UserResponse userResponse = userService.updateUser(USER_ID, userRequest);

                verify(userRepository).save(any(User.class));

                assertThat(userResponse.getId()).isEqualTo(USER_ID);
                assertThat(userResponse.getName()).isEqualTo(NEW_NAME);
                assertThat(userResponse.getEmail()).isEqualTo(NEW_EMAIL);
                assertThat(userResponse.getPassword()).isEqualTo(NEW_PASSWORD);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id로 사용자 정보를 수정할 경우")
        class Describe_with_non_existing_user_id {
            @DisplayName("404 Not found 상태 코드를 리턴한다")
            @Test
            void it_returns_404_code() {
                assertThatThrownBy(() -> userService.updateUser(1000L, userRequest))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
