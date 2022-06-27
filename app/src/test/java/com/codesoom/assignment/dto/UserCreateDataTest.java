package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateDataTest {

    @Test
    void testToString() {
        UserCreateData userCreateData = UserCreateData.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        assertThat(userCreateData.toString())
                .isEqualTo("UserCreateData{" +
                        "name='" + userCreateData.getName() + '\'' +
                        ", email='" + userCreateData.getEmail() + '\'' +
                        ", password='" + userCreateData.getPassword() + '\'' +
                        '}');
    }

    @Test
    void testBuilderToString() {
        String userData = UserCreateData.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .toString();

        assertThat(userData)
                .isEqualTo("UserCreateData.UserCreateDataBuilder(" +
                        "name=김철수" +
                        ", email=kim@gmail.com" +
                        ", password=1111" +
                        ')');
    }
}
