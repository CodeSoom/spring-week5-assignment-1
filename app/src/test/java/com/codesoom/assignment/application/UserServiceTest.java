package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.domain.UserRepository;
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
    private Long createUserId;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository);
        userData = UserData.builder()
                .name(NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();


        createUser = userService.createUser(userData);
        createUserId = createUser.getId();
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
    @DisplayName("유저 검색")
    void selectUser() {
        // when
        UserData user = userService.selectUser(createUserId);

        // then
        assertThat(user.getName()).isEqualTo(createUser.getName());
        assertThat(user.getEmail()).isEqualTo(createUser.getEmail());
    }

    @Test
    @DisplayName("유저 검색 실패")
    void selectUserFail() {
        // when
        // then
        assertThatThrownBy(() -> userService.selectUser(NOT_EXISTS_ID))
                .isInstanceOf(UserNotFoundException.class);

    }

    @Test
    @DisplayName("유저 리스트 검색")
    void selectUsers() {
        // when
        List<UserData> users = userService.selectUsers();

        // then
        assertThat(users.size()).isNotZero();
        assertThat(users.get(0).getName()).isEqualTo(NAME);
    }
}
