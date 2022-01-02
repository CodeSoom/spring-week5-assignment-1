package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.exception.UserEmailDuplicationException;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("UserService 클래스")
class UserServiceTest {

    @Autowired
    private UserService userService;

    List<UserRegistrationData> testUserRegistrationData = new ArrayList<>();

    List<UserModificationData> testUserModificationData = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testUserRegistrationData.add(UserRegistrationData.builder()
                .name("Hyuk")
                .password("!234")
                .email("pjh0819@naver.com")
                .build());

        testUserRegistrationData.add(UserRegistrationData.builder()
                .name("Hyuk1")
                .password("123$5")
                .email("pjh1234@naver.com")
                .build());

        testUserRegistrationData.add(UserRegistrationData.builder()
                .name("Hyuk2")
                .password("123$55")
                .email("pjh1111@naver.com")
                .build());

        testUserModificationData.add(UserModificationData.builder()
                .name("Update Hyuk")
                .password("123$")
                .build());
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록할_User가_주어진다면 {

            UserRegistrationData givenUserRegistrationData;

            @BeforeEach
            void prepare() {
                givenUserRegistrationData = testUserRegistrationData.get(0);
            }

            @Test
            void User를_생성하고_리턴한다() {
                User user = userService.createUser(givenUserRegistrationData);

                User foundUser = userService.getUser(user.getId());

                assertThat(foundUser.getName()).isEqualTo(givenUserRegistrationData.getName());
                assertThat(foundUser.getEmail()).isEqualTo(givenUserRegistrationData.getEmail());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 중복된_User의_Email이_주어진다면 {

            UserRegistrationData givenUserRegistrationData;

            @BeforeEach
            void prepare() {
                givenUserRegistrationData = testUserRegistrationData.get(2);
                userService.createUser(givenUserRegistrationData);
            }

            @Test
            void 중복된_email의_유저가_있다는_예외를_던진다() {
                assertThatThrownBy(() -> userService.createUser(givenUserRegistrationData))
                        .isInstanceOf(UserEmailDuplicationException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_User의_id와_수정할_User가_주어진다면 {

            UserModificationData givenUserModificationData;
            Long givenId;

            @BeforeEach
            void prepaer() {
                User user = userService.createUser(testUserRegistrationData.get(1));
                givenId = user.getId();
                givenUserModificationData = testUserModificationData.get(0);
            }

            @Test
            void 해당_id의_User를_수정하고_리턴한다() {
                userService.updateUser(givenId, givenUserModificationData);

                User foundUser = userService.getUser(givenId);

                assertThat(foundUser).isNotNull();
                assertThat(foundUser.getName()).isEqualTo(givenUserModificationData.getName());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록되지_않은_User의_id와_수정할_User가_주어진다면 {

            UserModificationData givenUserModificationData;
            Long givenInvalidId;

            @BeforeEach
            void prepaer() {
                User user = userService.createUser(testUserRegistrationData.get(0));
                userService.deleteUser(user.getId());

                givenInvalidId = user.getId();
                givenUserModificationData = testUserModificationData.get(0);
            }

            @Test
            void 등록된_User가_없다는_예외를_던진다() {
                assertThatThrownBy(() -> userService.updateUser(givenInvalidId, givenUserModificationData),
                        "등록된 User가 없으므로, 업데이트할 User가 없어야 합니다.")
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class deleteUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_User의_id가_주어진다면 {

            Long givenId;

            @BeforeEach
            void prepare() {
                User user = userService.createUser(testUserRegistrationData.get(0));
                givenId = user.getId();
            }

            @Test
            void 등록된_User를_삭제한다() {
                userService.deleteUser(givenId);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록되지_않은_User의_id가_주어진다면 {

            Long givenInvalidId;

            @BeforeEach
            void prepare() {
                User user = userService.createUser(testUserRegistrationData.get(0));
                userService.deleteUser(user.getId());
                givenInvalidId = user.getId();
            }

            @Test
            void 등록된_User가_없다는_예외를_던진다() {
                assertThatThrownBy(() -> userService.deleteUser(givenInvalidId),
                        "등록된 User가 없으므로, 삭제할 User가 없어야 합니다.")
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
