package com.codesoom.assignment.member.application;

import com.codesoom.assignment.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.codesoom.assignment.member.application.MemberCommand.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MemberCommand 클래스")
class MemberCommandTest {

    @Nested
    @DisplayName("Register toEntity 메소드는")
    class Describe_register_toEntity {
        @Test
        @DisplayName("커맨드 객체를 회원 엔티티로 변환한다.")
        void it_converts_member_entity() {
            Register.RegisterBuilder registerBuilder = Register.builder();

            System.out.println(registerBuilder.toString());

            registerBuilder.name("홍길동")
                    .password("test1234")
                    .email("test@gmail.com");

            Assertions.assertThat(registerBuilder.build().toEntity()).isInstanceOf(Member.class);
        }
    }

    @Nested
    @DisplayName("UpdateRequest toEntity 메소드는")
    class Describe_update_request_toEntity {
        @Test
        @DisplayName("커맨드 객체를 회원 엔티티로 변환한다.")
        void it_converts_member_entity() {
            UpdateRequest.UpdateRequestBuilder builder = UpdateRequest.builder();

            System.out.println(builder.toString());

            builder.id(1L)
                    .name("홍길동")
                    .password("test1234")
                    .email("test@gmail.com");

            Assertions.assertThat(builder.build().toEntity()).isInstanceOf(Member.class);
        }
    }

}