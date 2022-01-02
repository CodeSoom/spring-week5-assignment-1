package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
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

    List<UserData> testUserData = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testUserData.add(UserData.builder()
                .name("Hyuk")
                .password("!234")
                .email("pjh0819@naver.com")
                .build());

        testUserData.add(UserData.builder()
                .name("Update Hyuk")
                .password("123$")
                .email("pjh9999@naver.com")
                .build());
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class createUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록할_User가_주어진다면 {

            UserData givenUserData;

            @BeforeEach
            void prepare() {
                givenUserData = testUserData.get(0);
            }

            @Test
            void User를_생성하고_리턴한다() {
                User user = userService.createUser(givenUserData);

                User foundUser = userService.getUser(user.getId());

                assertThat(foundUser.getName()).isEqualTo(givenUserData.getName());
                assertThat(foundUser.getEmail()).isEqualTo(givenUserData.getEmail());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateUser_메소드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_User의_id와_수정할_User가_주어진다면 {

            UserData givenUserData;
            Long givenId;

            @BeforeEach
            void prepaer() {
                User user = userService.createUser(testUserData.get(0));
                givenId = user.getId();
                givenUserData = testUserData.get(1);
            }

            @Test
            void 해당_id의_User를_수정하고_리턴한다() {
                userService.updateUser(givenId, givenUserData);

                User foundUser = userService.getUser(givenId);

                assertThat(foundUser).isNotNull();
                assertThat(foundUser.getName()).isEqualTo(givenUserData.getName());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록되지_않은_User의_id와_수정할_User가_주어진다면 {

            UserData givenUserData;
            final Long givenInvalidId = Long.MAX_VALUE;

            @BeforeEach
            void prepaer() {
                givenUserData = testUserData.get(1);
            }

            @Test
            void 등록된_User가_없다는_예외를_던진다() {
                assertThatThrownBy(() -> userService.updateUser(givenInvalidId, givenUserData),
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
                User user = userService.createUser(testUserData.get(0));
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

            final Long givenId = Long.MAX_VALUE;

            @Test
            void 등록된_User가_없다는_예외를_던진다() {
                assertThatThrownBy(() -> userService.deleteUser(givenId),
                        "등록된 User가 없으므로, 삭제할 User가 없어야 합니다.")
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
