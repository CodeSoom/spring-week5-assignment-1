package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("UserServiceImpl 클래스")
class UserServiceImplTest {

    private static final String NAME = "ABC";
    private static final String EMAIL = "abc@hello.com";
    private static final String PASSWORD = "aa!@#5";

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        userService = new UserServiceImpl(mapper, userRepository);
    }

    private User getUser() {
        return User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private UserCreateRequest subject() {
            return UserCreateRequest.builder()
                    .name(NAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .build();
        }

        @Test
        @DisplayName("회원을 생성하여 반환한다")
        void it_returns_created_user() {
            User user = userService.create(subject());

            assertThat(user).isNotNull();
            assertThat(user.getId()).isNotNull();
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        private UserUpdateRequest subject() {
            final String prefix = "updated_";
            return UserUpdateRequest.builder()
                    .name(prefix + NAME)
                    .email(prefix + EMAIL)
                    .password(prefix + PASSWORD)
                    .build();
        }

        @Nested
        @DisplayName("수정 가능한 id가 주어지면")
        class Context_with_valid_id {
            private final UserUpdateRequest subject = subject();
            private Long id;

            @BeforeEach
            void setUp() {
                User user = userRepository.save(getUser());
                id = user.getId();
            }

            @Test
            @DisplayName("수정된 회원을 반환한다")
            void it_returns_updated_user() {
                User updatedUser = userService.update(id, subject);

                assertThat(updatedUser.getId()).isEqualTo(id);
                assertThat(updatedUser.getName()).isEqualTo(subject.getName());
                assertThat(updatedUser.getEmail()).isEqualTo(subject.getEmail());
                assertThat(updatedUser.getPassword()).isEqualTo(subject.getPassword());
            }
        }

        @Nested
        @DisplayName("수정 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                User user = userRepository.save(getUser());
                id = user.getId();

                userRepository.deleteById(id);
            }

            @Test
            void it_throws_exception() {
                assertThatThrownBy(() -> userService.update(id, subject()))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("삭제 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                User user = userRepository.save(getUser());
                id = user.getId();
            }

            @Test
            @DisplayName("삭제된 회원을 반환한다")
            void it_deletes_user() {
                User deletedUser = userService.delete(id);

                assertThat(deletedUser).isNotNull();
            }
        }

        @Nested
        @DisplayName("삭제 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private Long invalidId;

            @BeforeEach
            void setUp() {
                User user = userRepository.save(getUser());
                userRepository.delete(user);

                invalidId = user.getId();
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> userService.delete(invalidId))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
