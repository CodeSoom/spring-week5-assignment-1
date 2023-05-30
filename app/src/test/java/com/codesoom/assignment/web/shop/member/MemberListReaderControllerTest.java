package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberListReader;
import com.codesoom.assignment.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberController 의")
@WebMvcTest(MemberListReaderController.class)
class MemberListReaderControllerTest {
    @MockBean
    MemberListReader memberListReader;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        Member result = Member.builder()
                .name("유재석")
                .phone("01022222222")
                .build();

        given(memberListReader.read())
                .willReturn(List.of(result));
    }


    @Nested
    @DisplayName("GetMember 메소드는")
    class GetMember {

        @Nested
        @DisplayName("존재하는 회원을 조회할 경우")
        class ExistMember {

            @Test
            @DisplayName("해당 멤버리스트와 상태코드 200을 응답한다.")
            void returnMemberAndIsOk() throws Exception {
                given(memberListReader.read())
                        .willReturn(List.of());

                mockMvc.perform(get("/members")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk());
            }
        }
    }
}
