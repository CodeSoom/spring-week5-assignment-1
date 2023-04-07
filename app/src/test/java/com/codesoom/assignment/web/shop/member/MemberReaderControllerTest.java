package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.MemberNotFoundException;
import com.codesoom.assignment.application.member.MemberReader;
import com.codesoom.assignment.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MemberReaderController")
@WebMvcTest(MemberReaderController.class)
public class MemberReaderControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MemberReader memberReader;

    @Nested
    @DisplayName("Read 메소드는")
    class Describe_Read {

        @Nested
        @DisplayName("존재하는 멤버를 조회할 경우")
        class Context_with_exist_memberId {

            @Test
            @DisplayName("멤버와 상태코드 200을 응답한다.")
            void it_returns_member() throws Exception {
                Member createdMember = Member.builder()
                        .name("유재석")
                        .phone("01042222222")
                        .build();
                given(memberReader.read(anyLong())).willReturn(createdMember);

                mockMvc.perform(get("/members/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지않는 멤버를 조회할 경우")
        class Context_with_invalid_memberId {

            @Test
            @DisplayName("멤버와 상태코드 200을 응답한다.")
            void it_returns_member() throws Exception {

                given(memberReader.read(1000L)).willThrow(MemberNotFoundException.class);

                mockMvc.perform(get("/members/1000")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
