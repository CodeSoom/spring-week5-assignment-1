package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.dto.UserEmailDuplicateException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.convert.DurationFormat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("UserServiceImpl 클래스")
class UserServiceImplTest {

    private UserService userService;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private User TEST_USER;

    private Long TEST_ID = 1L;
    private String TEST_NAME = "name";
    private String TEST_EMAIL = "email@xxxx.com";
    private String TEST_PASSWORD = "1234";

    private String UPDATE_NAME = "updateName";
    private String UPDATE_EMAIL = "updateEmail@xxxx.com";
    private String UPDATE_PASSWORD = "13579";

    @BeforeEach
    void setUp() {

        TEST_USER = User.builder()
                .id(1L)
                .name("name")
                .email("email@xxxx.com")
                .password("1234")
                .build();

        userService = new UserServiceImpl(userRepository);

    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {

        @Nested
        @DisplayName("생성할 사용자가 있을 경우에")
        class Context_exist_user {

            UserData source;

            @BeforeEach
            void setUp() {

                source = UserData.builder()
                        .name(TEST_NAME)
                        .email(TEST_EMAIL)
                        .password(TEST_PASSWORD)
                        .build();

                given(userRepository.save(any(User.class))).will(invocation -> {
                    User user = invocation.getArgument(0);
                    return user.builder()
                            .id(TEST_ID)
                            .name(TEST_NAME)
                            .email(TEST_EMAIL)
                            .password(TEST_PASSWORD)
                            .build();
                });

            }

            @Test
            @DisplayName("UserData 객체를 통하여 User 객체를 생성후 반환한다")
            void It_create_return_user() throws Exception {

                User createUser = userService.createUser(source);

                assertEquals(TEST_ID, createUser.getId());
                assertEquals(TEST_NAME, createUser.getName());
                assertEquals(TEST_EMAIL, createUser.getEmail());
                assertEquals(TEST_PASSWORD, createUser.getPassword());

                verify(userRepository).save(any(User.class));

            }

        }

        @Nested
        @DisplayName("생성할 사용자 이메일이 이미 존재한다면")
        class Context_duplicate_email {

            UserData source;

            @BeforeEach
            void setUp() throws Exception{

                source = UserData.builder()
                        .name(TEST_NAME)
                        .email(TEST_EMAIL)
                        .password(TEST_PASSWORD)
                        .build();

              given(userService.createUser(source)).willThrow(new UserEmailDuplicateException());
//                given(userService.createUser(source)).willAnswer(invocation -> {
//                    throw  new UserEmailDuplicateException();
//                });

            }

            @Test
            @DisplayName("UserEmailDuplicateException 예외를 던진다")
            void It_create_return_user() throws Exception {

                org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        userService.createUser(source)).as("이미 가입된 메일이 존재합니다.").isInstanceOf(UserEmailDuplicateException.class);

            }

        }

    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateUset {

        @Nested
        @DisplayName("수정할 사용자 정보가 있을 경우에")
        class Context_exist_update_user {

            UserData userData;

            @BeforeEach
            void setUp() {

                userData = UserData.builder()
                        .name(UPDATE_NAME)
                        .email(UPDATE_EMAIL)
                        .password(UPDATE_PASSWORD)
                        .build();

                given(userRepository.findById(TEST_ID)).willReturn(Optional.of(TEST_USER));

            }

            @Test
            @DisplayName("아이디와 수정 정보를 입력받아 사용자 정보를 수정한다")
            void It_return_update_user() {

                User updateUser = userService.updateUser(TEST_ID, userData);

                assertEquals(TEST_ID, updateUser.getId());
                assertEquals(UPDATE_NAME, updateUser.getName());
                assertEquals(UPDATE_EMAIL, updateUser.getEmail());
                assertEquals(UPDATE_PASSWORD, updateUser.getPassword());

                verify(userRepository).findById(TEST_ID);

            }

        }

    }

}

