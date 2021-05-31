package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserFixtures;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("UserService 클래스의")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Mapper mapper;

    private User userAlice;
    private User userBob;
    private UserData userDataAlice;

    @BeforeEach
    void prepareUsers() {
        userAlice = UserFixtures.alice();
        userBob = UserFixtures.bob();
        userDataAlice = UserFixtures.validAliceData();
    }

    @Nested
    @DisplayName("get 메서드는")
    class Describe_get {

        @Nested
        @DisplayName("만약 주어진 ID의 사용자가 저장되어 있을 경우")
        class Context_with_existed_user_id {

            @BeforeEach
            void mocking() {
                given(userRepository.findById(userBob.getId()))
                        .willReturn(Optional.ofNullable(userBob));
            }

            @Test
            @DisplayName("해당 사용자를 반환한다")
            void It_returns_user() {
                User user = userService.get(userBob.getId());

                assertThat(user.getName()).isEqualTo(userBob.getName());
            }
        }

        @Nested
        @DisplayName("만약 주어진 ID의 사용자가 저장되어 있지 않을 경우")
        class Context_with_not_existed_user_id {

            @BeforeEach
            void mocking() {
                given(userRepository.findById(userBob.getId()))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            void It_returns_user() {
                assertThatThrownBy(() -> userService.get(userBob.getId()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }

    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @BeforeEach
        void mocking() {
            given(mapper.map(userDataAlice, User.class))
                    .willReturn(userAlice);
            given(userRepository.save(userAlice))
                    .willReturn(userAlice);
        }

        @Test
        @DisplayName("주어진 사용자 데이터로 사용자를 저장한다")
        void It_returns_created_user() {
            User user = userService.create(userDataAlice);

            assertThat(user.getName()).isEqualTo(userAlice.getName());
            assertThat(user.getEmail()).isEqualTo(userAlice.getEmail());
            assertThat(user.getPassword()).isEqualTo(userAlice.getPassword());
        }
    }

}
