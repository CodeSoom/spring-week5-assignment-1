package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User 클래스")
class UserTest {
    
    @Nested
    @DisplayName("builder는")
    class Describe_creationWithBuilder{

        User givenUser;

        @BeforeEach
        void setUp(){
            givenUser = User.builder()
                    .id(1L)
                    .name("김철수")
                    .email("abc@exmaple.com")
                    .pw("1234")
                    .build();
        }

        @Test
        @DisplayName("User를 리턴한다.")
        void it_returns_user(){
            assertThat(givenUser.getId()).isEqualTo(1L);
            assertThat(givenUser.getName()).isEqualTo("김철수");
            assertThat(givenUser.getEmail()).isEqualTo("abc@exmaple.com");
            assertThat(givenUser.getPw()).isEqualTo("1234");
        }
    }

    @Nested
    @DisplayName("changeWith 메소드는")
    class Describe_changeWith{

        @Nested
        @DisplayName("User 객체가 주어지면")
        class Context_with_a_user{

            User givenUser;

            @BeforeEach
            void setUp(){
                givenUser = User.builder()
                        .id(1L)
                        .name("김철수")
                        .email("abc@exmaple.com")
                        .pw("1234")
                        .build();

                givenUser.changeWith(User.builder()
                        .name("이영희")
                        .email("123@exmaple.com")
                        .pw("abcd")
                        .build()
                );
            }

            @Test
            @DisplayName("주어진 객체의 내용으로 수정한 User를 리턴한다.")
            void it_returns_user(){
                assertThat(givenUser.getId()).isEqualTo(1L);
                assertThat(givenUser.getName()).isEqualTo("이영희");
                assertThat(givenUser.getEmail()).isEqualTo("123@exmaple.com");
                assertThat(givenUser.getPw()).isEqualTo("abcd");
            }
        }
    }




}
