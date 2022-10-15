package com.codesoom.assignment.domain;

import com.codesoom.assignment.common.MemberSampleFactory;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("MemberRepository 인터페이스")
class MemberRepositoryTest {

    @DataJpaTest
    class JpaTest {
        @Autowired
        MemberRepository memberRepository;

        public MemberRepository getMemberRepository() {
            return memberRepository;
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll extends JpaTest {
        private final List<Member> givenMembers = new ArrayList<>();

        @BeforeEach
        void prepare() {
            givenMembers.add(getMemberRepository().save(MemberSampleFactory.createMember(1L)));
            givenMembers.add(getMemberRepository().save(MemberSampleFactory.createMember(2L)));
        }

        @Test
        @DisplayName("모든 회원을 리턴한다")
        void it_returns_all_data() {
            List<Member> actualMembers = getMemberRepository().findAll();

            assertThat(actualMembers).hasSize(givenMembers.size());
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
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
            void it_returns_member_info() {
                Optional<Member> actualMember = getMemberRepository().findById(givenMember.getId());

                assertThat(actualMember).isNotEmpty();
                assertThat(actualMember.get().getName()).isEqualTo(givenMember.getName());
                assertThat(actualMember.get().getEmail()).isEqualTo(givenMember.getEmail());
                assertThat(actualMember.get().getPassword()).isEqualTo(givenMember.getPassword());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 ID가 주어지면")
        class Context_with_non_existed_id extends JpaTest {
            @Test
            @DisplayName("'결과 없음'을 리턴한다")
            void it_returns_optional_empty() {
                assertThat(getMemberRepository().findById(-1L)).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("새로운 회원정보가 주어지면")
        class Context_with_new_member_info extends JpaTest {
            private final Member givenMember = MemberSampleFactory.createMember();

            @Test
            @DisplayName("등록하고 리턴한다")
            void it_returns_registered_member() {
                Member actualMember = getMemberRepository().save(givenMember);

                assertThat(actualMember.getId()).isNotNull();
                assertThat(actualMember.getName()).isEqualTo(givenMember.getName());
                assertThat(actualMember.getPassword()).isEqualTo(givenMember.getPassword());
                assertThat(actualMember.getEmail()).isEqualTo(givenMember.getEmail());

            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("회원정보가 주어지면")
        class Context_with_member_info extends JpaTest {
            private Member givenMember;

            @BeforeEach
            void prepare() {
                givenMember = getMemberRepository().save(MemberSampleFactory.createMember());
            }

            @Test
            @DisplayName("회원정보를 삭제한다")
            void it_deletes_member_info() {
                getMemberRepository().delete(givenMember);

                Optional<Member> actual = getMemberRepository().findById(givenMember.getId());

                assertThat(actual).isEmpty();
            }
        }
    }
}
