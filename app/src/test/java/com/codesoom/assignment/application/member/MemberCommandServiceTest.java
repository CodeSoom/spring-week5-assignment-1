package com.codesoom.assignment.application.member;

import com.codesoom.assignment.application.member.MemberCommand.Register;
import com.codesoom.assignment.application.member.implement.MemberCommandServiceImpl;
import com.codesoom.assignment.common.MemberSampleFactory;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.controller.member.MemberDtoMapper;
import com.codesoom.assignment.domain.member.Member;
import com.codesoom.assignment.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("MemberCommandService 클래스")
class MemberCommandServiceTest {

    @DataJpaTest
    class JpaTest {
        @Autowired
        private MemberRepository repository;
        private MemberCommandService service;

        public MemberRepository getMemberRepository() {
            return repository;
        }

        public MemberCommandService getMemberService() {
            if (service == null) {
                service = new MemberCommandServiceImpl(repository);
            }
            return service;
        }
    }

    @Nested
    @DisplayName("createMember 메소드는")
    class Describe_createMember {
        @Nested
        @DisplayName("새로운 회원정보가 주어지면")
        class Context_with_new_member extends JpaTest {
            @Test
            @DisplayName("등록하고 회원정보를 리턴한다")
            void it_returns_registered_member() {
                final Register command = Register.builder()
                        .name("홍길동")
                        .password("test1234")
                        .email("hogn@test.com")
                        .build();

                final Member savedMember = getMemberService().createMember(command);

                assertThat(savedMember.getName()).isEqualTo(command.getName());
                assertThat(savedMember.getPassword()).isEqualTo(command.getPassword());
                assertThat(savedMember.getEmail()).isEqualTo(command.getEmail());
            }
        }
    }

    @Nested
    @DisplayName("updateMember 메소드는")
    class Describe_updateMember {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id extends JpaTest {
            private Member savedMember;

            @BeforeEach
            void prepare() {
                savedMember = getMemberRepository().save(MemberSampleFactory.createMember());
            }

            @Test
            @DisplayName("회원정보를 수정하고 리턴한다")
            void it_returns_modified_member() {
                final MemberCommand.UpdateRequest command = MemberCommand.UpdateRequest.builder()
                        .id(savedMember.getId())
                        .name("임꺽정")
                        .password("test1111")
                        .email("test@gmail.com").build();

                final Member updatedMember = getMemberService().updateMember(command);

                assertThat(updatedMember.getId()).isEqualTo(command.getId());
                assertThat(updatedMember.getName()).isEqualTo(command.getName());
                assertThat(updatedMember.getPassword()).isEqualTo(command.getPassword());
                assertThat(updatedMember.getEmail()).isEqualTo(command.getEmail());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id extends JpaTest {
            private final Long MEMBER_ID = 9999L;
            private final MemberCommand.UpdateRequest command = MemberDtoMapper.INSTANCE.of(MEMBER_ID, MemberSampleFactory.createUpdateParam());

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> getMemberService().updateMember(command)).isInstanceOf(MemberNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteMember 메소드는")
    class Describe_deleteMember {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id extends JpaTest {
            private Member savedMember;
            @BeforeEach
            void prepare() {
                savedMember = getMemberRepository().save(MemberSampleFactory.createMember());
            }

            @Test
            @DisplayName("회원정보를 삭제한다")
            void it_deletes_member() {
                final int beforeCnt = getMemberRepository().findAll().size();

                getMemberService().deleteMember(savedMember.getId());

                final int afterCnt = getMemberRepository().findAll().size();

                final Optional<Member> findMember = getMemberRepository().findById(savedMember.getId());

                assertThat(afterCnt).isEqualTo(beforeCnt - 1);
                assertThat(findMember).isEmpty();
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id extends JpaTest {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> getMemberService().deleteMember(9999L)).isInstanceOf(MemberNotFoundException.class);
            }
        }
    }
}
