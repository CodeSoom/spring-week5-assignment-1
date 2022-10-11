package com.codesoom.assignment.application.member;

import com.codesoom.assignment.application.member.implement.MemberQueryServiceImpl;
import com.codesoom.assignment.common.MemberSampleFactory;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.domain.member.Member;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("MemberQueryService 클래스")
class MemberQueryServiceTest {

    @DataJpaTest
    class JpaTest {
        @Autowired
        MemberRepository repository;
        MemberQueryService service;

        public MemberRepository getMemberRepository() {
            return repository;
        }

        public MemberQueryService getMemberService() {
            if (service == null) {
                service = new MemberQueryServiceImpl(repository);
            }
            return service;
        }
    }

    @Nested
    @DisplayName("getMembers 메소드는")
    class Describe_getMembers extends JpaTest {
        private final List<Member> givenMember = new ArrayList<>();

        @BeforeEach
        void prepare() {
            givenMember.add(getMemberRepository().save(MemberSampleFactory.createMember()));
            givenMember.add(getMemberRepository().save(MemberSampleFactory.createMember()));
        }

        @Test
        @DisplayName("등록된 모든 회원정보를 리턴한다")
        void it_returns_all_member_info() {
            final List<MemberInfo> actualMember = getMemberService().getMembers();

            assertThat(actualMember).hasSize(givenMember.size());
        }
    }

    @Nested
    @DisplayName("getMember 메소드는")
    class Describe_getMember {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id extends JpaTest {
            private Member givenMember;

            @BeforeEach
            void prepare() {
                givenMember = getMemberRepository().save(MemberSampleFactory.createMember());
            }

            @Test
            @DisplayName("회원정보를 리턴한다")
            void it_returns_member() {
                final MemberInfo actualMember = getMemberService().getMember(givenMember.getId());

                assertThat(actualMember.getId()).isEqualTo(givenMember.getId());
                assertThat(actualMember.getName()).isEqualTo(givenMember.getName());
                assertThat(actualMember.getPassword()).isEqualTo(givenMember.getPassword());
                assertThat(actualMember.getEmail()).isEqualTo(givenMember.getEmail());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id extends JpaTest {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> getMemberService().getMember(9999L)).isInstanceOf(MemberNotFoundException.class);
            }
        }
    }
}