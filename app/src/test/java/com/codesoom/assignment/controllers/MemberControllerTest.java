package com.codesoom.assignment.controllers;

import com.codesoom.assignment.MemberNotFoundException;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberController 의")
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    MemberData memberData;

    @BeforeEach
    public void init() {
        memberData = MemberData.builder()
                .name("유재석")
                .phone("01022222222")
                .build();

        Member result = Member.builder()
                .name("유재석")
                .phone("01022222222")
                .build();

        given(memberService.create(memberData)).willReturn(result);

        given(memberService.create(any())).willReturn(result);

        given(memberService.getMember(1L))
                .willReturn(result);

        given(memberService.getMembers())
                .willReturn(List.of(result));
    }

    @Nested
    @DisplayName("Create 메소드는")
    class Create {

        @Nested
        @DisplayName("모든 파라미터가 유효할 때")
        class ValidAllParameter {

            @Test
            @DisplayName("정상적으로 데이터를 만들고 201을 응답한다.")
            public void createValidMemberWithAllParameter() throws Exception {
                mockMvc.perform(post("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(memberData.toString()))
                        .andExpect(content().string(containsString(memberData.getName())))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("특정 파라미터가 유효성 검증을 실패할 때")
        class SpecificInvalidParameter {

            @Test
            @DisplayName("400을 응답한다.")
            void returnBadRequest() throws Exception {
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
            @DisplayName("404응답한다.")
            void returnNotFoundStatus() throws Exception {
                given(memberService.getMember(1000L))
                        .willThrow(new MemberNotFoundException(1000L));

                mockMvc.perform(get("/members/1000"))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("존재하는 회원을 조회할 경우")
        class ExistMember {

            @Test
            @DisplayName("해당 멤버 와 상태코드 200을 응답한다.")
            void returnMemberAndIsOk() throws Exception {
                given(memberService.getMembers())
                        .willReturn(List.of());

                mockMvc.perform(get("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("Update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("멤버가 존재하는 경우 ")
        class context_with_exist_member {

            @Test
            @DisplayName("해당 멤버를 업데이트하고 반환한다. ")
            void it_returns_valid_member() throws Exception {
                MemberData updateRequestMember = MemberData.builder()
                        .name("변경된유재석")
                        .phone("01047105883")
                        .build();

                mockMvc.perform(patch("/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(updateRequestMember.getName())));
            }
        }
    }

}
