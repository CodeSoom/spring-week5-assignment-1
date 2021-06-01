package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private String userUrl = "/users";

    private ObjectMapper objectMapper = new ObjectMapper();

    UserData setUser(Long id) {

        return UserData.builder()
                .id(id)
                .email("test" + id + "@gamil.com")
                .password("testPW" + id)
                .name("테스트" + id)
                .build();
    }

    String setJSONUserById(Long id) throws JsonProcessingException {
        return setJSONUserByObj(setUser(id));
    }

    String setJSONUserByObj(UserData userData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userData);
    }

    @Nested
    @DisplayName("userCreate() 메소드는")
    class Describe_Create {
        @Nested
        @DisplayName("모든 값이 입력 된다면")
        class Context_Valid_Create {
            private String userDataJSON;
            private Long id = 1L;

            @BeforeEach
            void setUpValidCreate() throws JsonProcessingException {
                UserData sourceUserData = setUser(id);
                userDataJSON = setJSONUserByObj(sourceUserData);
                given(userService.createUser(any(UserData.class))).willReturn(sourceUserData);
            }

            @Test
            @DisplayName("Respose Status를 201를 반환한다.")
            void create_valid() throws Exception {
                mockMvc.perform(post(userUrl)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJSON))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("입력되지 않은 값이 있다면")
        class Context_Invalid_Create{
            private String userDataJson;
            private Long id = 100L;
//            private String errorMessage = "잘못된 요청입니다. 파라미터를 확인해 주세요.1";

            @BeforeEach
            void setUpInvalidCreate() throws JsonProcessingException {
                UserData sourceUserData = setUser(id);
                sourceUserData.setEmail("");
                sourceUserData.setName("");
                sourceUserData.setPassword("");

                userDataJson = setJSONUserByObj(sourceUserData);
            }

            @Test
            @DisplayName("Response Status를 400을 반환한다.")
            void create_invalid() throws Exception {
                mockMvc.perform(post(userUrl)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson))
                        .andExpect(status().isBadRequest());
            }

        }
    }

}
