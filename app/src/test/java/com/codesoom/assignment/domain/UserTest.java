package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.constant.UserConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User createUser;
    private UserData modifyUserData;

    @BeforeEach
    void setup() {
        createUser = User.builder()
                .id(DEFAULT_ID)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        modifyUserData = UserData.builder()
                .name(CHANGE_NAME)
                .email(CHANGE_EMAIL)
                .password(CHANGE_PASSWORD)
                .build();
    }

    @Test
    @DisplayName("유저 생성")
    void createUser(){
        // when
        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // then
        assertThat(user.name()).isEqualTo(NAME);
        assertThat(user.email()).isEqualTo(EMAIL);
    }

    @Test
    @DisplayName("유저 수정")
    void modifyUser(){
        // when
        createUser.modifyUser(modifyUserData);

        assertThat(createUser.name()).isEqualTo(CHANGE_NAME);
        assertThat(createUser.email()).isEqualTo(CHANGE_EMAIL);
    }
}
