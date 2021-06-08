package com.codesoom.assignment.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member class")
class MemberTest {
    private final Long DEFAULT_ID = 1L;
    private final String NAME = "MEMBER";
    private final String PASSWORD = "MEMBER123";
    private final String EMAIL = "member@google.com";

    @Nested
    @DisplayName("Lombok 사용")
    class contextLombokAnnotation {
        @Test
        @DisplayName("@Builder 테스트")
        void creationWithBuilder() {
            Member.MemberBuilder memberBuilder = Member.builder()
                    .id(DEFAULT_ID)
                    .name(NAME)
                    .password(PASSWORD)
                    .email(EMAIL);

            Member member = memberBuilder.build();

            assertThat(memberBuilder.toString()).isEqualTo(String.valueOf(memberBuilder));

            assertThat(member.getId()).isEqualTo(DEFAULT_ID);
            assertThat(member.getName()).isEqualTo(NAME);
            assertThat(member.getPassword()).isEqualTo(PASSWORD);
            assertThat(member.getEmail()).isEqualTo(EMAIL);
        }

        @Test
        @DisplayName("@AllArgsConstructor 테스트")
        void creationWithAllArgsConstructor() {
            Member member = new Member(DEFAULT_ID, NAME, PASSWORD, EMAIL);

            assertThat(member.getId()).isEqualTo(DEFAULT_ID);
            assertThat(member.getName()).isEqualTo(NAME);
            assertThat(member.getPassword()).isEqualTo(PASSWORD);
            assertThat(member.getEmail()).isEqualTo(EMAIL);
        }

        @Test
        @DisplayName("@NoArgsConstructor 테스트")
        void creationWithNoArgsConstructor() {
            Member member = new Member();

            assertThat(member.getName()).isBlank();
            assertThat(member.getPassword()).isBlank();
            assertThat(member.getEmail()).isBlank();
        }
    }

    @Test
    @DisplayName("changeWith() 테스트")
    void changeWith() {
        Member member = Member.builder().build();

        member.changeWith(NAME, PASSWORD, EMAIL);

        assertThat(member.getName()).isEqualTo(NAME);
        assertThat(member.getPassword()).isEqualTo(PASSWORD);
        assertThat(member.getEmail()).isEqualTo(EMAIL);
    }

}
