package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserDeleteReport;
import com.codesoom.assignment.dto.UserUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

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
            private UserCreateRequest requestUser;

            @BeforeEach
            void setUp() {
                requestUser = UserCreateRequest.builder()
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

                assertThat(savedUser).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(requestUser);
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
            private UserUpdateRequest requestUser;

            @BeforeEach
            void setUp() {
                savedUser = userCommandService.createUser(
                        UserCreateRequest.builder()
                                .email("before@before.com")
                                .name("김 코")
                                .password("before")
                                .build()
                );

                requestUser = UserUpdateRequest.builder()
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

                assertThat(updatedUser).usingRecursiveComparison()
                        .ignoringFields("id", "email")
                        .isEqualTo(requestUser);
            }
        }

        @Nested
        @DisplayName("요청하는 User 가 존재하지 않는 경우")
        class Context_with_non_existence_user {
            private UserUpdateRequest requestUser;

            @BeforeEach
            void setUp() {
                requestUser = UserUpdateRequest.builder()
                        .name("김 코")
                        .password("after")
                        .build();
            }

            @Test
            @DisplayName("사용자를 찾지 못했다는 예외를 던진다")
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
                        UserCreateRequest.builder()
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
            @DisplayName("사용자를 찾지 못했다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(
                        () -> userCommandService.deleteUser(INVALID_USER_ID)
                ).isExactlyInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteUsers 메서드는")
    @SpringBootTest
    class Describe_deleteUsers {

        @Autowired
        private UserRepository userRepository;

        @Nested
        @DisplayName("저장되어있는 user 의 id가 주어지면 ")
        class Context_with_existing_user_id {
            private Set<Long> deletedIds;

            @BeforeEach
            void setUp() {
                User savedUser1 = userCommandService.createUser(
                        UserCreateRequest.builder()
                                .email("a@a.com")
                                .name("김 코")
                                .password("a")
                                .build()
                );
                User savedUser2 = userCommandService.createUser(
                        UserCreateRequest.builder()
                                .email("b@b.com")
                                .name("김 코")
                                .password("b")
                                .build()
                );

                deletedIds = Set.of(savedUser1.getId(), savedUser2.getId());
            }

            @Test
            @DisplayName("user 들을 삭제하고 삭제한 user ids 와 삭제하지 못한 user ids(빈 set)를 리턴한다")
            void it_returns_delete_user_report() {

                UserDeleteReport userDeleteReport = userCommandService.deleteUsers(deletedIds);

                assertThat(userRepository.findAllById(deletedIds)).isEmpty();
                assertThat(userRepository.findAllById(userDeleteReport.getDeletedSuccessIds())).isEmpty();

                assertThat(userDeleteReport.getDeletedSuccessIds()).isEqualTo(deletedIds);
                assertThat(userDeleteReport.getDeletedFailIds()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장되어있지 않은 user 의 id가 주어지면 ")
        class Context_with_non_existence_user_id {
            private Long deleteId;

            @BeforeEach
            void setUp() {
                User savedUser = userCommandService.createUser(
                        UserCreateRequest.builder()
                                .email("a@a.com")
                                .name("김 코")
                                .password("a")
                                .build()
                );
                deleteId = savedUser.getId();
            }

            @Test
            @DisplayName("user 들을 삭제하고 삭제한 user ids 와 삭제하지 못한 user ids 를 리턴한다")
            void it_returns_delete_user_report() {
                UserDeleteReport userDeleteReport = userCommandService.deleteUsers(Set.of(deleteId, INVALID_USER_ID));

                assertThat(userRepository.findById(deleteId)).isEmpty();
                assertThat(userRepository.findAllById(userDeleteReport.getDeletedSuccessIds())).isEmpty();

                assertThat(userDeleteReport.getDeletedSuccessIds()).contains(deleteId);
                assertThat(userDeleteReport.getDeletedFailIds()).contains(INVALID_USER_ID);
            }
        }
    }
}
