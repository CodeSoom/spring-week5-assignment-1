package com.codesoom.assignment.controller.member;

import com.codesoom.assignment.application.member.MemberCommand;
import com.codesoom.assignment.common.MemberSampleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MemberDtoMapper 클래스")
class MemberDtoMapperTest {

    @Nested
    @DisplayName("of(RequestParam) 메소드는")
    class Describe_of_request_param {
        @Nested
        @DisplayName("유효한 요청 파라미터가 주어지면")
        class Context_with_valid_request_parameter {
            @Test
            @DisplayName("Register 객체를 리턴한다")
            void it_returns_register() {
                MemberDto.RequestParam member = MemberSampleFactory.createRequestParam();

                final MemberCommand.Register actual = MemberDtoMapper.INSTANCE.of(member);

                assertThat(actual).isInstanceOf(MemberCommand.Register.class);
            }
        }

        @Nested
        @DisplayName("요청 파라미터가 Null이면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_register() {
                final MemberCommand.Register actual = MemberDtoMapper.INSTANCE.of(null);

                assertThat(actual).isNull();
            }
        }

    }

    @Nested
    @DisplayName("of(id, UpdateParam) 메소드는")
    class Describe_of_id_and_request_param {
        @Nested
        @DisplayName("유효한 요청 파라미터가 주어지면")
        class Context_with_valid_request_parameter {
            @Test
            @DisplayName("UpdateReq 객체를 리턴한다")
            void it_returns_register() {
                final Long id = 1L;

                final MemberCommand.UpdateRequest actual = MemberDtoMapper.INSTANCE.of(id, MemberSampleFactory.createUpdateParam());

                assertThat(actual).isInstanceOf(MemberCommand.UpdateRequest.class);
            }
        }

        @Nested
        @DisplayName("모든 파라미터가 Null이면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_null() {
                final MemberCommand.UpdateRequest actual = MemberDtoMapper.INSTANCE.of(null, null);

                assertThat(actual).isNull();
            }
        }

        @Nested
        @DisplayName("ID가 Null이면")
        class Context_with_id_null {
            @Test
            @DisplayName("ID 필드가 Null인 객체를 리턴한다")
            void it_returns_null() {
                final MemberCommand.UpdateRequest actual = MemberDtoMapper.INSTANCE.of(null, MemberSampleFactory.createUpdateParam());

                assertThat(actual.getId()).isNull();
                assertThat(actual.getName()).isNotNull();
                assertThat(actual.getPassword()).isNotNull();
            }
        }

        @Nested
        @DisplayName("UpdateParam이 Null이면")
        class Context_with_requestparam_null {
            @Test
            @DisplayName("UpdateParam 필드들이 Null인 객체를 리턴한다")
            void it_returns_null() {
                final Long id = 1L;

                final MemberCommand.UpdateRequest actual = MemberDtoMapper.INSTANCE.of(id, null);

                assertThat(actual.getId()).isNotNull();
                assertThat(actual.getName()).isNull();
                assertThat(actual.getPassword()).isNull();
                assertThat(actual.getEmail()).isNull();

            }
        }
    }
}
