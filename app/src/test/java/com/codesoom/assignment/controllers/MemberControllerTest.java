package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.MemberService;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.dto.MemberData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberController")
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    MemberData memberData;

    @BeforeEach
    public void init() {
        MemberData.builder()
                .name("김유신")
                .phone("01047105883")
                .build();

        Member result = Member.builder()
                .name("김유신")
                .phone("01047105883")
                .build();

        given(memberService.create(memberData)).willReturn(result);

        given(memberService.create(any())).willReturn(result);

        given(memberService.getMember(1000L))
                .willThrow(new ProductNotFoundException(1000L));

        given(memberService.getMember(1L))
                .willReturn(result);

    }

    @Nested
    @DisplayName("Create 메소드는")
    class Create {

        @Nested
        @DisplayName("모든 파라미터가 유효할 때")
        class ValidAllParameter {

            @Test
            @DisplayName("정상적으로 멤버를 만든다.")
            public void createValidMemberWithAllParameter() throws Exception {
                mockMvc.perform(post("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(memberData.toString()))
                        .andExpect(content().string(containsString("김유신")))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("특정 파라미터가 유효성 검증을 실패할 때")
        class SpecificInvalidParameter {

            @Test
            @DisplayName("400 에러를 던진다.")
            void throwBadRequest() throws Exception {
                MemberData memberData = MemberData.builder()
                        .name("name")
                        .phone("")
                        .build();

                mockMvc.perform(post("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(memberData.toString()))
                        .andExpect(status().isBadRequest());
            }
        }
    }


    @Nested
    @DisplayName("GetMember 메소드는")
    class GetMember {

        @Nested
        @DisplayName("존재하지 않는 회원을 조회할 경우")
        class NotExistMember {

            @Test
            @DisplayName("404 에러를 던진다.")
            void throwNotFoundException() throws Exception {
                mockMvc.perform(get("/members/1000"))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
