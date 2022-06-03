package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void createWithBuilder() {
        User user = User.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        assertThat(user.getName()).isEqualTo("김철수");
        assertThat(user.getEmail()).isEqualTo("kim@gmail.com");
        assertThat(user.getPassword()).isEqualTo("1111");
    }

    @Test
    void update() {
        User user = User.builder()
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .build();

        user.update(User.builder()
                .name("김영희")
                .email("young@gmail.com")
                .password("1234")
                .build());

        assertThat(user.getName()).isEqualTo("김영희");
        assertThat(user.getEmail()).isEqualTo("young@gmail.com");
        assertThat(user.getPassword()).isEqualTo("1234");
    }

    @Test
    void createWithNoAttributes() {
        User user = new User();

        assertThat(user.getName()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getPassword()).isNull();
    }

    @Test
    void testBuilderToString() {
        String user = User.builder()
                .id(1L)
                .name("김철수")
                .email("kim@gmail.com")
                .password("1111")
                .toString();

        assertThat(user)
                .isEqualTo("User.UserBuilder(" +
                        "id=1" +
                        ", name=김철수" +
                        ", email=kim@gmail.com" +
                        ", password=1111" +
                        ')');
    }
}
