package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("update 메소드에 유효한 파라미터를 전달했을 경우")
    void updateWithValidParam() {

        //arrange
        User targetUser = User.builder()
                .name("name")
                .email("email")
                .password("password")
                .build();

        String updateName = "updateName";
        String updateEmail = "updateEmail";
        String updatePassword = "updatePassword";

        User updateParam = User.builder()
                .name(updateName)
                .email(updateEmail)
                .password(updatePassword)
                .build();

        //Act
        targetUser.update(updateParam);

        //assert
        assertThat(targetUser.getName()).isEqualTo(updateName);
        assertThat(targetUser.getEmail()).isEqualTo(updateEmail);
        assertThat(targetUser.getPassword()).isEqualTo(updatePassword);
    }

}
