package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void builder(){
        User user = User.builder()
                .id(1L)
                .name("name")
                .email("email@naver.com")
                .password("1234")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email@naver.com");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(User.builder().toString()).isNotNull();
    }

    @Test
    void change(){
        User user = User.builder()
                .id(1L)
                .name("name")
                .email("email@naver.com")
                .password("1234")
                .build();

        user.change("updateName","updateEmail@naver.com","12345");

        assertThat(user.getName()).isEqualTo("updateName");
        assertThat(user.getEmail()).isEqualTo("updateEmail@naver.com");
        assertThat(user.getPassword()).isEqualTo("12345");
    }
}