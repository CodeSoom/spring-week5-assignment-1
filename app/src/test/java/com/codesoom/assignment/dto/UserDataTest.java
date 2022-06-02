package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDataTest {

    @Test
    void testToString() {
        UserData userData = UserData.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        assertThat(userData.toString())
                .isEqualTo("UserData{" +
                        "name='" + userData.getName() + '\'' +
                        ", email='" + userData.getEmail() + '\'' +
                        ", password='" + userData.getPassword() + '\'' +
                        '}');
    }

    @Test
    void testBuilderToString() {
        String userData = UserData.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .toString();

        assertThat(userData)
                .isEqualTo("UserData.UserDataBuilder(" +
                        "name=김철수" +
                        ", email=kim@gmail.com" +
                        ", password=1111" +
                        ')');
    }
}
