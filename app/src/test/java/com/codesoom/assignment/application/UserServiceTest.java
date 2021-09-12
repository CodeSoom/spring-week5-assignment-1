package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.codesoom.assignment.constant.UserConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserData userData;
    private UserData createUser;
    private UserData modifyUserData;
    private Long createUserId;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository, DozerBeanMapperBuilder.buildDefault());

        userData = UserData.builder()
                .name(NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();


        createUser = userService.createUser(userData);
        createUserId = createUser.getId();

        modifyUserData = UserData.builder()
                .name(CHANGE_NAME)
                .email(CHANGE_EMAIL)
                .password(CHANGE_PASSWORD)
                .build();
    }

    @Test
    @DisplayName("유저 생성")
    void createUser() {
        // when
        UserData user = userService.createUser(userData);

        // then
        assertThat(user.getName()).isEqualTo(user.getName());
        assertThat(user.getEmail()).isEqualTo(user.getEmail());
    }


    @Test
    @DisplayName("회원 검색")
    void selectUser() {
        // when
        UserData user = userService.selectUser(createUserId);

        // then
        assertThat(user.getName()).isEqualTo(createUser.getName());
        assertThat(user.getEmail()).isEqualTo(createUser.getEmail());
    }

    @Test
    @DisplayName("회원 검색 실패 - 존재하지 않는 회원 ID")
    void selectUserFail() {
        // when
        // then
        assertThatThrownBy(() -> userService.selectUser(NOT_EXISTS_ID))
                .isInstanceOf(UserNotFoundException.class);

    }

    @Test
    @DisplayName("회원 리스트 검색")
    void selectUsers() {
        // when
        List<UserData> users = userService.selectUsers();

        // then
        assertThat(users.size()).isNotZero();
        assertThat(users.get(0).getName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("수정하려는 회원을 찾아 존재 시 회원 수정")
    void modifyUser() {
        // when
        UserData user = userService.modifyUser(createUserId, modifyUserData);

        // then
        assertThat(user.getName()).isEqualTo(CHANGE_NAME);
        assertThat(user.getEmail()).isEqualTo(CHANGE_EMAIL);
    }

    @Test
    @DisplayName("회원 수정 실패 - 존재하지 않는 ID")
    void notExistsUserModifyFail() {
        // when
        // then
        assertThatThrownBy(() -> userService.modifyUser(NOT_EXISTS_ID, modifyUserData))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("삭제하려는 회원을 찾아 존재 시 회원 삭제")
    void deleteUser() {
        // when
        userService.deleteUser(createUserId);

        // then
        assertThatThrownBy(() -> userService.selectUser(createUserId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("회원 삭제 실패 - 존재하지 않는 ID")
    void notExistsUserDeleteFail() {
        // when
        // then
        assertThatThrownBy(() -> userService.deleteUser(NOT_EXISTS_ID))
                .isInstanceOf(UserNotFoundException.class);
    }
}
