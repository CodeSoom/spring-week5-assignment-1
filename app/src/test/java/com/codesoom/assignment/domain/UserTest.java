package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User 클래스")
class UserTest {

    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .id(1L)
                .name("김철수")
                .email("abc@exmaple.com")
                .pw("1234")
                .build();
    }


    @Nested
    @DisplayName("builder는")
    class Describe_creationWithBuilder{

        @Test
        @DisplayName("User를 리턴한다.")
        void it_returns_user(){
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getName()).isEqualTo("김철수");
            assertThat(user.getEmail()).isEqualTo("abc@exmaple.com");
            assertThat(user.getPw()).isEqualTo("1234");
        }
    }

    @Nested
    @DisplayName("changeWith 메소드는")
    class Describe_changeWith{

        @BeforeEach
        void setUp(){
            user.changeWith(User.builder()
                    .name("이영희")
                    .email("123@exmaple.com")
                    .pw("abcd")
                    .build()
            );
        }
        @Test
        @DisplayName("수정한 User를 리턴한다.")
        void it_returns_user(){
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getName()).isEqualTo("이영희");
            assertThat(user.getEmail()).isEqualTo("123@exmaple.com");
            assertThat(user.getPw()).isEqualTo("abcd");
        }
    }




}
