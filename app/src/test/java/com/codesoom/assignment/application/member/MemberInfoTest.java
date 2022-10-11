package com.codesoom.assignment.application.member;

import com.codesoom.assignment.common.MemberSampleFactory;
import com.codesoom.assignment.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MemberInfo 클래스")
class MemberInfoTest {

    @Nested
    @DisplayName("MemberInfo 생성자는")
    class Describe_MemberInfo_constructor {
        @Nested
        @DisplayName("Member 엔티티가 주어지면")
        class Context_with_member_entity {
            private final Member givenMember = MemberSampleFactory.createMember(1L);

            @Test
            @DisplayName("MemberInfo 객체로 변환한다")
            void it_converts_memberinfo_object() {
                Assertions.assertThat(new MemberInfo(givenMember)).isInstanceOf(MemberInfo.class);
            }
        }
    }

}