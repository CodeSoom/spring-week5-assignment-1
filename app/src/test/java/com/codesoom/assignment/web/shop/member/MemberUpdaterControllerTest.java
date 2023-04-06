package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberUpdater;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.web.shop.member.dto.MemberUpdateRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberUpdaterController 의")
@WebMvcTest(MemberUpdaterController.class)
class MemberUpdaterControllerTest {
    @MockBean
    MemberUpdater memberUpdater;

    @Autowired
    MockMvc mockMvc;

    MemberUpdateRequest updateRequestMember;

    @BeforeEach
    public void init() {
        Member result = Member.builder()
                .name("변경된유재석")
                .phone("01022222222")
                .build();

        updateRequestMember = MemberUpdateRequest.builder()
                .id(1000L)
                .name("변경된유재석")
                .phone("01022222222")
                .build();

        given(memberUpdater.update(any())).willReturn(result);
    }

    @Nested
    @DisplayName("Update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("멤버가 존재하는 경우에 ")
        class context_with_exist_member {

            @Test
            @DisplayName("해당 멤버를 업데이트하고 반환한다. ")
            void it_returns_valid_member() throws Exception {
                mockMvc.perform(patch("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateRequestMember.toString()))
                        .andExpect(content().string(containsString("변경된유재석")))
                        .andExpect(status().isOk());

            }
        }
    }
}
