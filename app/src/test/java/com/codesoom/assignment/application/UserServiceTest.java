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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 클래스의")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private final User userAlice = UserFixtures.alice();
    private final User userBob = UserFixtures.bob();
    private final UserData validUserDataAlice = UserFixtures.validAliceData();
    private final UserData validUserDataBob = UserFixtures.validBobData();

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    Mapper mapper;

    @Nested
    @DisplayName("get 메서드는")
    class Describe_get {

        @Nested
        @DisplayName("만약 주어진 ID의 사용자를 찾을 수 있다면")
        class Context_with_existed_user_id {

            @BeforeEach
            void mocking() {
                given(userRepository.findById(userBob.getId()))
                        .willReturn(Optional.ofNullable(userBob));
            }

            @Test
            @DisplayName("해당 사용자를 리턴한다")
            void It_returns_user() {
                User user = userService.get(userBob.getId());

                assertThat(user.getName()).isEqualTo(userBob.getName());
            }
        }

        @Nested
        @DisplayName("만약 주어진 ID의 사용자를 찾을 수 없다면")
        class Context_with_not_existed_user_id {

            @BeforeEach
            void mocking() {
                given(userRepository.findById(userBob.getId()))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            void It_throws_user_not_found_exception() {
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
            given(mapper.map(validUserDataAlice, User.class))
                    .willReturn(userAlice);
            given(userRepository.save(userAlice))
                    .willReturn(userAlice);
        }

        @Test
        @DisplayName("주어진 사용자 데이터로 사용자를 생성한 후 생성된 사용자를 리턴한다")
        void It_returns_created_user() {
            User user = userService.create(validUserDataAlice);

            assertThat(user.getName()).isEqualTo(userAlice.getName());
            assertThat(user.getEmail()).isEqualTo(userAlice.getEmail());
            assertThat(user.getPassword()).isEqualTo(userAlice.getPassword());
        }
    }

    @Nested
    @DisplayName("patch 메서드는")
    class Describe_patch {

        @Nested
        @DisplayName("만약 주어진 ID의 사용자를 찾을 수 있다면")
        class Context_with_existed_user_id_and_valid_user_data {

            @BeforeEach
            void mocking() {
                given(userRepository.findById(userAlice.getId()))
                        .willReturn(Optional.ofNullable(userAlice));
                given(mapper.map(validUserDataBob, User.class))
                        .willReturn(userBob);
            }

            @Test
            @DisplayName("해당 사용자를 주어진 사용자 데이터로 수정한 후 리턴한다")
            void It_patches_the_user_with_user_data() {
                User userAliceToBob = userService.patch(userAlice.getId(),
                                                        validUserDataBob);

                assertThat(userAliceToBob.getName())
                        .isEqualTo(validUserDataBob.getName());
                assertThat(userAliceToBob.getEmail())
                        .isEqualTo(validUserDataBob.getEmail());
                assertThat(userAliceToBob.getPassword())
                        .isEqualTo(validUserDataBob.getPassword());
            }
        }

        @Nested
        @DisplayName("만약 주어진 ID의 사용자를 찾을 수 없다면")
        class Context_with_not_existed_user_id {
            private final Long invalidUserId = -1L;

            @BeforeEach
            void mocking() {
                given(userRepository.findById(invalidUserId))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            void It_throws_user_not_found_exception() {
                assertThatThrownBy(() -> userService.patch(invalidUserId, validUserDataBob))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 주어진 ID의 사용자를 찾을 수 있다면")
        class Context_with_existed_user_id {

            @BeforeEach
            void mocking() {
                given(userRepository.findById(userAlice.getId()))
                        .willReturn(Optional.ofNullable(userAlice));

                doNothing().when(userRepository)
                           .delete(userAlice);
            }

            @Test
            @DisplayName("해당 사용자를 제거한다")
            void It_deletes_the_user() {
                userService.delete(userAlice.getId());

                verify(userRepository).delete(any(User.class));
            }
        }

        @Nested
        @DisplayName("만약 주어진 ID의 사용자를 찾을 수 없다면")
        class Context_with_not_existed_user_id {
            private final Long invalidUserId = -1L;

            @BeforeEach
            void mocking() {
                given(userRepository.findById(invalidUserId))
                        .willThrow(UserNotFoundException.class);
            }

            @Test
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            void It_throws_user_not_found_exception() {
                assertThatThrownBy(() -> userService.delete(invalidUserId))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
