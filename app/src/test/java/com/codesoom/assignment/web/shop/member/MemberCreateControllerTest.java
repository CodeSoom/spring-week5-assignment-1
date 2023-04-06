package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberCreator;
import com.codesoom.assignment.domain.Member;
import com.codesoom.assignment.web.shop.member.dto.MemberCreateRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberCreateController")
@WebMvcTest(MemberCreateController.class)
class MemberCreateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberCreator memberCreator;

    @BeforeEach
    public void init() {
        Member result = Member.builder()
                .name("유재석")
                .phone("01022222222")
                .build();

        given(memberCreator.create(any())).willReturn(result);
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {

        @Nested
        @DisplayName("모든 파라미터가 유효할 때")
        class Context_with_valid_paramter {

            @Test
            @DisplayName("만들어진 멤버와 201을 응답한다")
            void it_returns_isCreated() throws Exception {
                MemberCreateRequest createRequest = MemberCreateRequest
                        .builder()
                        .name("유재석")
                        .phone("01022222222")
                        .build();

                mockMvc.perform(post("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createRequest.toString()))
                        .andExpect(content().string(containsString(createRequest.getName())))
                        .andExpect(status().isCreated());
            }
        }

    }

}
