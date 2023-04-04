package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.MemberService;
import com.codesoom.assignment.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MemberController")
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    MemberService memberService;


    @Nested
    @DisplayName("Create 메소드는")
    class Create {

        @Nested
        @DisplayName("모든 파라미터가 유효할 때")
        class ValidAllParameter {

            @Test
            @DisplayName("정상적으로 멤버를 만든다.")
            public void createValidMemberWithAllParameter() {
                MemberController memberController = new MemberController(memberService);
                Member member = memberController.create("김유신", "01047105883");
                assertThat(member.getName()).isEqualTo("김유신");
                assertThat(member.getPhone()).isEqualTo("01047105883");
            }
        }
    }
}
