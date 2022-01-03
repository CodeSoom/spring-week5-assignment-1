package com.codesoom.assignment.application;

import com.codesoom.assignment.errors.UserEmailAlreadyExistedException;
import com.codesoom.assignment.errors.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.dto.UserModificationData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("UserService 테스트")
class UserServiceTest {
    private UserService userService;

    private Mapper mapper;

    private final UserRepository userRepository = mock(UserRepository.class);

    private static final Long DELETED_USER_ID = 1000L;
    private static final Long NOT_EXISTED_ID = 9999L;

    @BeforeEach
    void setUp() {
        mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserService(mapper, userRepository);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_이메일이라면 {

            private User user;

            private UserRegistrationData userRegistrationData;

            @BeforeEach
            void setUp() {
                userRegistrationData = UserRegistrationData.builder()
                        .name("홍길동")
                        .email("test@test.com")
                        .password("asdqwe1234")
                        .build();

                user = mapper.map(userRegistrationData, User.class);

                given(userRepository.save(any(User.class))).willReturn(user);
            }

            @Test
            @DisplayName("새로운 회원을 생성하여 리턴한다")
            void 새로운_회원을_생성하여_리턴한다() {
                User createdUser = userService.create(userRegistrationData);

                verify(userRepository).save(any(User.class));

                assertThat(createdUser.getName()).isEqualTo(user.getName());
                assertThat(createdUser.getEmail()).isEqualTo(user.getEmail());
                assertThat(createdUser.getPassword()).isEqualTo(user.getPassword());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이미_존재하는_이메일이라면 {

            private UserRegistrationData existedUser;

            private static final String ALREADY_EXISTED_EMAIL = "existedEmail@test.com";

            @BeforeEach
            void setUp() {
                given(userRepository.existsByEmail(ALREADY_EXISTED_EMAIL))
                        .willThrow(new UserEmailAlreadyExistedException(ALREADY_EXISTED_EMAIL));

                existedUser = UserRegistrationData.builder()
                        .email(ALREADY_EXISTED_EMAIL)
                        .name("tester")
                        .password("asdqwe1234")
                        .build();
            }

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> userService.create(existedUser))
                        .isInstanceOf(UserEmailAlreadyExistedException.class);

                verify(userRepository).existsByEmail(ALREADY_EXISTED_EMAIL);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class update_메소드는 {

        private User user;
        private UserModificationData source;

        @BeforeEach
        void setUp() {
            user = User.builder()
                    .name("홍길동")
                    .email("test@test.com")
                    .password("asdqwe1234")
                    .build();

            source = UserModificationData.builder()
                    .name("철수")
                    .password("asdfg")
                    .build();

            given(userRepository.findById(1L)).willReturn(Optional.of(user));
            given(userRepository.findById(DELETED_USER_ID)).willReturn(Optional.empty());
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_ID라면 {

            @Test
            @DisplayName("수정된 회원 정보를 리턴한다")
            void 수정된_회원_정보를_리턴한다() {
                User updatedUser = userService.update(1L, source);

                verify(userRepository).findById(1L);

                assertThat(updatedUser.getName()).isEqualTo(source.getName());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_ID라면 {

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> userService.update(NOT_EXISTED_ID, source))
                        .isInstanceOf(UserNotFoundException.class);

                verify(userRepository).findById(NOT_EXISTED_ID);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 삭제된_ID라면 {

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> userService.update(DELETED_USER_ID, source))
                        .isInstanceOf(UserNotFoundException.class);

                verify(userRepository).findById(DELETED_USER_ID);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메소드는 {

        private User source;

        @BeforeEach
        void setUp() {
            source = User.builder()
                    .name("철수")
                    .email("cjftn@test.com")
                    .password("asdfg")
                    .build();
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하는_ID라면 {

            @Test
            @DisplayName("회원 정보를 삭제하고 삭제된 회원을 리턴한다")
            void 회원_정보를_삭제하고_삭제된_회원을_리턴한다() {
                given(userRepository.findById(1L)).willReturn(Optional.of(source));

                User deletedUser = userService.deleteUserById(1L);

                verify(userRepository).findById(1L);
                verify(userRepository).delete(source);

                assertThat(deletedUser.getName()).isEqualTo(source.getName());
                assertThat(deletedUser.getEmail()).isEqualTo(source.getEmail());
                assertThat(deletedUser.isDeleted()).isTrue();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 존재하지_않는_ID라면 {

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> userService.deleteUserById(DELETED_USER_ID))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
