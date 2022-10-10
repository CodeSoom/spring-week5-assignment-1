package com.codesoom.assignment.member.domain;

import com.codesoom.assignment.member.common.MemberFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Member 클래스")
class MemberTest {

    @Nested
    @DisplayName("Builder 는")
    class Describe_builder {
        @Nested
        @DisplayName("Input 매개변수가 주어지면")
        class Context_with_input_parameters {
            private final Member given = new Member(1L, "홍길동", "test1234", "hong@test.com");

            @Test
            @DisplayName("Member 객체를 생성한다")
            void it_generate_member_object() {
                Member actual = Member.builder()
                        .id(1L)
                        .name("홍길동")
                        .password("test1234")
                        .email("hong@test.com")
                        .build();

                assertThat(actual).isInstanceOf(Member.class);
                assertThat(actual.getId()).isEqualTo(given.getId());
                assertThat(actual.getName()).isEqualTo(given.getName());
                assertThat(actual.getPassword()).isEqualTo(given.getPassword());
                assertThat(actual.getEmail()).isEqualTo(given.getEmail());
            }
        }

        @Nested
        @DisplayName("이름, 비밀번호, 이메일에 빈 값이 주어지면")
        class Context_with_empty_parameters {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {

                assertThatThrownBy(() -> {
                    Member.builder()
                            .id(1L)
                            .name(null)
                            .password("")
                            .email("")
                            .build();
                }).isInstanceOf(IllegalArgumentException.class);

            }

        }
    }

    @Nested
    @DisplayName("modifyMemberInfo 메소드는")
    class Describe_modifyMemberInfo {
        @Nested
        @DisplayName("회원정보가 주어지면")
        class Context_with_modified_member_info {
            private final Member givenMember = MemberFactory.createProduct(1L);
            @Test
            @DisplayName("수정된 정보를 반영한다")
            void it_reflects_modified_info() {
                Member modifiedMember = Member.builder()
                        .id(1L)
                        .name("홍길동")
                        .password("test1234")
                        .email("test@gmail.com")
                        .build();

                givenMember.modifyMemberInfo(modifiedMember);

                assertThat(givenMember.getName()).isEqualTo(modifiedMember.getName());
                assertThat(givenMember.getPassword()).isEqualTo(modifiedMember.getPassword());
                assertThat(givenMember.getEmail()).isEqualTo(modifiedMember.getEmail());
            }
        }
    }

}