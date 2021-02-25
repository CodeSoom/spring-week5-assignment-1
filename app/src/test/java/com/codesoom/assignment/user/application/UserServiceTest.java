package com.codesoom.assignment.user.application;

import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserData;
import com.codesoom.assignment.user.dto.UserSaveRequestDto;
import com.codesoom.assignment.user.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@DisplayName("UserService 클래스")
class UserServiceTest {
    private static final Long NOT_EXIST_ID = -1L;

    private static final String USER_NAME = "test";
    private static final String USER_PASSWORD = "pass";
    private static final String USER_EMAIL = "test@test.com";

    private static final String UPDATE_NAME = "new_test";
    private static final String UPDATE_PASSWORD = "new_pass";
    private static final String UPDATE_EMAIL = "new@test.com";

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("createUser 메서드는")
    class Describe_createUser {
        UserSaveRequestDto requestDto;

        @BeforeEach
        void setUp() {
            requestDto = getUserSaveDto();
        }

        @DisplayName("새로운 사용자 추가되고, 사용자 정보를 리턴한다")
        @Test
        void it_returns_user() {
            UserData actual = userService.createUser(requestDto);

            assertAll(
                    () -> assertThat(userService.getUsersInformation()).isNotEmpty(),
                    () -> assertThat(actual.getId()).isNotNull(),
                    () -> assertThat(actual.getName()).isEqualTo(USER_NAME),
                    () -> assertThat(actual.getEmail()).isEqualTo(USER_EMAIL),
                    () -> assertThat(actual.getPassword()).isEqualTo(USER_PASSWORD)
            );
        }
    }

    @Nested
    @DisplayName("getUsers 메서드는")
    class Describe_getUsers {

        @Nested
        @DisplayName("등록된 사용자가 존재하면")
        class Context_with_users {

            @BeforeEach
            void setUp() {
                UserSaveRequestDto requestDto = getUserSaveDto();
                userService.createUser(requestDto);
            }

            @DisplayName("등록된 사용자 목록을 리턴한다")
            @Test
            void It_return_users() {
                List<UserData> actual = userService.getUsersInformation();

                assertThat(actual).hasSize(1);
                assertThat(actual.get(0).getName()).isEqualTo(USER_NAME);
                assertThat(actual.get(0).getEmail()).isEqualTo(USER_EMAIL);
            }
        }

        @Nested
        @DisplayName("등록된 사용자가 존재하지 않으면")
        class Context_without_users {

            @DisplayName("비어있는 사용자 목록을 리턴한다")
            @Test
            void It_return_empty_users() {
                assertThat(userService.getUsersInformation()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getUser 메서드는")
    class Describe_getUser {
        Long givenId;

        @Nested
        @DisplayName("등록된 사용자 id가 존재하면")
        class Context_with_exist_user_id {

            @BeforeEach
            void setUp() {
                UserSaveRequestDto requestDto = getUserSaveDto();
                UserData savedUser = userService.createUser(requestDto);
                givenId = savedUser.getId();
            }

            @DisplayName("등록된 사용자 id로 찾고자하는 사용자를 리턴한다")
            @Test
            void It_return_user() {
                UserData actual = userService.getUserInformation(givenId);

                assertAll(
                        () -> assertThat(actual.getId()).isEqualTo(givenId),
                        () -> assertThat(actual.getEmail()).isEqualTo(USER_EMAIL),
                        () -> assertThat(actual.getName()).isEqualTo(USER_NAME),
                        () -> assertThat(actual.getPassword()).isEqualTo(USER_PASSWORD)
                );
            }
        }

        @Nested
        @DisplayName("등록된 사용자 id가 존재하지 않으면")
        class Context_with_not_existed_user_id {

            @BeforeEach
            void setUp() {
                givenId = NOT_EXIST_ID;
            }

            @DisplayName("예외를 던진다.")
            @Test
            void It_throws_exception() {
                assertThatExceptionOfType(UserNotFoundException.class)
                        .isThrownBy(() -> userService.getUserInformation(givenId));
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메서드는")
    class Describe_updateUser {
        Long givenId;
        UserUpdateRequestDto updateRequestDto;

        @Nested
        @DisplayName("등록된 사용자 id가 존재하면")
        class Context_with_exist_user_id {

            @BeforeEach
            void setUp() {
                UserSaveRequestDto requestDto = getUserSaveDto();
                UserData savedUser = userService.createUser(requestDto);
                givenId = savedUser.getId();
                updateRequestDto = getUpdateRequest();
            }

            @DisplayName("수정된 사용자를 리턴한다")
            @Test
            void It_return_updated_user() {
                UserData actual = userService.updateUser(givenId, updateRequestDto);

                assertAll(
                        () -> assertThat(actual.getPassword()).isEqualTo(UPDATE_PASSWORD),
                        () -> assertThat(actual.getEmail()).isEqualTo(UPDATE_EMAIL),
                        () -> assertThat(actual.getName()).isEqualTo(UPDATE_NAME),
                        () -> assertThat(actual.getId()).isEqualTo(givenId)
                );
            }
        }

        @Nested
        @DisplayName("등록된 사용자 id가 존재하지 않으면")
        class Context_with_not_existed_user_id {

            @BeforeEach
            void setUp() {
                givenId = NOT_EXIST_ID;
                updateRequestDto = getUpdateRequest();
            }

            @DisplayName("예외를 던진다.")
            @Test
            void It_throws_exception() {
                assertThatExceptionOfType(UserNotFoundException.class)
                        .isThrownBy(() -> userService.updateUser(givenId, updateRequestDto));
            }
        }
    }

    @Nested
    @DisplayName("deleteUser 메서드는")
    class Describe_deleteUser {
        Long givenId;

        @Nested
        @DisplayName("등록된 사용자 id가 존재하면")
        class Context_with_exist_user_id {

            @BeforeEach
            void setUp() {
                UserSaveRequestDto requestDto = getUserSaveDto();
                UserData savedProduct = userService.createUser(requestDto);
                givenId = savedProduct.getId();
            }

            @DisplayName("삭제 대상인 사용자를 삭제한다")
            @Test
            void It_delete_user() {
                userService.deleteUser(givenId);

                assertThat(userService.getUsersInformation()).isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 사용자 id가 존재하지 않으면")
        class Context_with_not_existed_user_id {

            @BeforeEach
            void setUp() {
                givenId = NOT_EXIST_ID;
            }

            @DisplayName("예외를 던진다.")
            @Test
            void It_throws_exception() {
                assertThatExceptionOfType(UserNotFoundException.class)
                        .isThrownBy(() -> userService.deleteUser(givenId));
            }
        }
    }

    private UserSaveRequestDto getUserSaveDto() {
        return UserSaveRequestDto.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
    }

    private UserUpdateRequestDto getUpdateRequest() {
        return UserUpdateRequestDto.builder()
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .password(UPDATE_PASSWORD)
                .build();
    }
}
