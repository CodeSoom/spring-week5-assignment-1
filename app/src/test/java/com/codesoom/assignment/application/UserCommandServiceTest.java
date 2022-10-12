package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserCommandServiceTest {
    private static final long INVALID_USER_ID = 0L;

    @Autowired
    UserCommandService userCommandService;

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        @Nested
        @DisplayName("User 가 주어진다면")
        class Context_with_user {
            private UserRequest requestUser;

            @BeforeEach
            void setUp() {
                requestUser = UserRequest.builder()
                        .email("a@a.com")
                        .name("김 코")
                        .password("123")
                        .build();
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("User 를 저장하고 리턴한다")
            void it_returns_user() {
                User savedUser = userCommandService.createUser(requestUser);

                assertThat(savedUser.getEmail()).isEqualTo(requestUser.getEmail());
                assertThat(savedUser.getName()).isEqualTo(requestUser.getName());
                assertThat(savedUser.getPassword()).isEqualTo(requestUser.getPassword());
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        @Nested
        @DisplayName("요청하는 User 가 존재하는 경우")
        class Context_with_user {
            private User savedUser;
            private UserRequest requestUser;

            @BeforeEach
            void setUp() {
                savedUser = userCommandService.createUser(
                        UserRequest.builder()
                                .email("before@before.com")
                                .name("김 코")
                                .password("before")
                                .build()
                );

                requestUser = UserRequest.builder()
                        .email("after@after.com")
                        .name("김 딩")
                        .password("after")
                        .build();
            }

            @AfterEach
            void after() {
                userCommandService.deleteAll();
            }

            @Test
            @DisplayName("User 를 수정하고 리턴한다")
            void it_returns_updated_user() {
                User updatedUser = userCommandService.updateUser(savedUser.getId(), requestUser);

                assertThat(updatedUser.getEmail()).isEqualTo(requestUser.getEmail());
                assertThat(updatedUser.getName()).isEqualTo(requestUser.getName());
                assertThat(updatedUser.getPassword()).isEqualTo(requestUser.getPassword());
            }
        }

        @Nested
        @DisplayName("요청하는 User 가 존재하지 않는 경우")
        class Context_with_non_existence_user {
            private UserRequest requestUser;

            @BeforeEach
            void setUp() {
                requestUser = UserRequest.builder()
                        .email("after@after.com")
                        .name("김 코")
                        .password("after")
                        .build();
            }

            @Test
            @DisplayName("사용자가 존재하지 않다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(
                        () -> userCommandService.updateUser(INVALID_USER_ID, requestUser)
                ).isExactlyInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class Describe_deleteUser {
        @Nested
        @DisplayName("저장되어있는 user 의 id가 주어지면 ")
        @SpringBootTest
        class Context_with_existing_user_id {
            private Long deleteId;

            @Autowired
            private UserRepository userRepository;

            @BeforeEach
            void setUp() {
                User savedUser = userCommandService.createUser(
                        UserRequest.builder()
                                .email("before@before.com")
                                .name("김 코")
                                .password("before")
                                .build()
                );
                deleteId = savedUser.getId();
            }

            @Test
            @DisplayName("user 를 삭제하고 user id 를 리턴한다")
            void it_returns_deleted_user_id() {
                Long deletedUserId = userCommandService.deleteUser(deleteId);

                assertThat(userRepository.findById(deletedUserId)).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장되어있지 않은 user 의 id가 주어지면 ")
        class Context_with_non_existence_user_id {
            @Test
            @DisplayName("사용자가 존재하지 않다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(
                        () -> userCommandService.deleteUser(INVALID_USER_ID)
                ).isExactlyInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUsers 메서드는")
    class Describe_deleteUsers {
        @Nested
        @DisplayName("저장되어있는 user 의 id가 주어지면 ")
        @SpringBootTest
        class Context_with_existing_user_id {
            private Long deleteId1;
            private Long deleteId2;

            @Autowired
            private UserRepository userRepository;

            @BeforeEach
            void setUp() {
                User savedUser1 = userCommandService.createUser(
                        UserRequest.builder()
                                .email("a@a.com")
                                .name("김 코")
                                .password("a")
                                .build()
                );
                User savedUser2 = userCommandService.createUser(
                        UserRequest.builder()
                                .email("b@b.com")
                                .name("김 코")
                                .password("b")
                                .build()
                );
                deleteId1 = savedUser1.getId();
                deleteId2 = savedUser2.getId();
            }

            @Test
            @DisplayName("user 들을 삭제하고 user id 들을 리턴한다")
            void it_returns_deleted_user_ids() {
                List<Long> deletedIds = userCommandService.deleteUsers(Arrays.asList(deleteId1, deleteId2));

                assertThat(userRepository.findAllById(deletedIds)).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장되어있지 않은 user 의 id가 주어지면 ")
        class Context_with_non_existence_user_id {
            private Long deleteId;

            @BeforeEach
            void setUp() {
                User savedUser = userCommandService.createUser(
                        UserRequest.builder()
                                .email("a@a.com")
                                .name("김 코")
                                .password("a")
                                .build()
                );
                deleteId = savedUser.getId();
            }

            @Test
            @DisplayName("사용자가 존재하지 않다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(
                        () -> userCommandService.deleteUsers(Arrays.asList(deleteId, INVALID_USER_ID))
                ).isExactlyInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
