package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.member.MemberDeleter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberDeleterController.class)
@DisplayName("MemberDeleterController")
public class MemberDeleterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberDeleter memberDeleter;

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("존재하는 멤버의 아이디일 경우")
        class Context_with_valid_memberId {

            @Test
            @DisplayName("해당 멤버를 삭제하고 상태코드 204를 응답한다.")
            void it_returns_noContents() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.delete("/members/1000"))
                        .andExpect(status().isNoContent());

                verify(memberDeleter).delete(1000L);
            }
        }
    }
}
