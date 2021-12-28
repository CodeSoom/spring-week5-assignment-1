package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스는")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    List<UserData> userDatas = new ArrayList<>();

    @BeforeEach
    void setUp() {
        UserData userData = UserData.builder()
                .name("Hyuk")
                .password("!234")
                .email("pjh0819@naver.com")
                .build();

        IntStream.range(0, 5).forEach(i -> {
            userData.setId(Long.valueOf(i));
            userDatas.add(userData);
        });
    }

    @Nested
    @DisplayName("POST /user 요청은")
    class Describe_post{

        @Nested
        @DisplayName("등록된 User가 주어진다면")
        class Context_with_user {

            UserData givenUserData;
            User givenUser = new User();

            @BeforeEach
            void prepare() {
                givenUserData = userDatas.get(0);
                givenUser.change(givenUserData.getName(), givenUserData.getPassword(), givenUserData.getEmail());
                given(userService.createUser(any(UserData.class))).willReturn(givenUser);
            }

            @Test
            @DisplayName("User를 생성하고, 201(Created)와 user를 응답합니다.")
            void it_create_user_return_created_and_user() throws Exception {
                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenUserData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(givenUserData.getName()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("POST /user/{id} 요청은")
    class Describe_post_id{

        @Nested
        @DisplayName("수정할 id와 user가 주어진다면")
        class Context_with_id_and_user {

            UserData givenUserData;
            User givenUser = new User();
            Long givenId;

            @BeforeEach
            void prepare() {
                givenUserData = userDatas.get(0);

                given(userService.updateUser(eq(givenId), any(UserData.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            UserData userData = invocation.getArgument(1);

                            givenUser.change(userData.getName(), userData.getPassword(), userData.getEmail());
                            givenUser.setId(id);

                            return userData;
                        });
            }

            @Test
            @DisplayName("User를 업데이트하고, 200(ok)와 user를 응답합니다.")
            void it_update_user_return_ok_and_user() throws Exception {
                mockMvc.perform(post("/user/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userDataToContent(givenUserData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(givenUserData.getName()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("Delete /user/{id} 요청은")
    class Describe_update {

        @Nested
        @DisplayName("삭제할 id가 주어진다면")
        class Context_with_id {

            Long givenId = 1L;

            @Test
            @DisplayName("204(No Content)과 빈값을 응답합니다.")
            void it_delete_user_return_noContent() throws Exception {
                mockMvc.perform(delete("/user/" + givenId))
                        .andExpect(status().isNoContent())
                        .andDo(print());
            }
        }
    }

    private String userDataToContent(UserData userData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userData);
    }
}
