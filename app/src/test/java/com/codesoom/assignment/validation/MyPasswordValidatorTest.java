package com.codesoom.assignment.validation;

import org.junit.jupiter.api.*;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MyPasswordValidatorTest {

    private static MyPasswordValidator myPasswordValidator;
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

    @BeforeAll
    static void beforeAll() {
        myPasswordValidator = new MyPasswordValidator();
        myPasswordValidator.initialize(null);
    }
    @Nested
    @DisplayName("isValid 메소드는")
    class Describe_isValid {
        
        @Nested
        @DisplayName("올바른 값이 주어지면")
        class Context_with_valid_value{
            final String value = "aasd22#11";
            @Test
            @DisplayName("true를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isTrue();
            }
        }

        @Nested
        @DisplayName("길이가 8 보다 작으면")
        class Context_When_length_is_less_than_8 {
            final String value = "aasd#11";
            @Test
            @DisplayName("false를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isFalse();
            }
        }

        @Nested
        @DisplayName("길이가 16 보다 크면")
        class Context_When_length_is_greater_than_16 {
            final String value = "aasd#11wdkqwjdlkqwdjlk";
            @Test
            @DisplayName("false를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isFalse();
            }
        }

        @Nested
        @DisplayName("숫자가 포함 되지 않으면")
        class Context_without_number {
            final String value = "qwdqwdtertert##";
            @Test
            @DisplayName("false를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isFalse();
            }
        }

        @Nested
        @DisplayName("특수문자가 포함 되지 않으면")
        class Context_without_special_char {
            final String value = "qwdqwdter123";
            @Test
            @DisplayName("false를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isFalse();
            }
        }

        @Nested
        @DisplayName("영문이 포함 되지 않으면")
        class Context_without_eng {
            final String value = "$&%*&333##123";
            @Test
            @DisplayName("false를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isFalse();
            }
        }

        @Nested
        @DisplayName("한글이 포함 되면")
        class Context_with_kor {
            final String value = "asdadㅁ##!*3";
            @Test
            @DisplayName("false를 리턴한다.")
            void it_returns_false() {
                assertThat(myPasswordValidator.isValid(value, context)).isFalse();
            }
        }
    }
}
