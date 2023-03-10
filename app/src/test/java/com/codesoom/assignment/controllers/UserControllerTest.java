package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateDto;
import com.codesoom.assignment.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    @DisplayName("create")
    public void create() throws Exception{
        //given
        UserCreateDto user = UserCreateDto.builder()
                .name("name")
                .email("email")
                .password("password")
                .build();
        //when
        mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        verify(userService,times(1)).create(any(UserCreateDto.class));
    }

    @Test
    @DisplayName("create_Valid_Error")
    public void createInValid_Arg() throws Exception{
        //given
        UserCreateDto user = UserCreateDto.builder()
                .name("name")
                .password("password")
                .build();

        //when
        mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 입력으로 에러가 발생."))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.validation.email").value("이메일을 입력하세요"))
                .andDo(print());

        //then
        verifyNoMoreInteractions(userService);
    }
    @Test
    @DisplayName("create")
    public void createInValid() throws Exception{
        //given
        UserCreateDto user = UserCreateDto.builder()
                .name("name")
                .password("password")
                .build();

        //when
        mockMvc.perform(post("/user")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 입력으로 에러가 발생."))
                .andExpect(jsonPath("$.validation.email").value("이메일을 입력하세요"))
                .andDo(print());

    }

    @Test
    @DisplayName("update")
    public void updateValid() throws Exception{
        //given
        UserRequest request = UserRequest.builder()
                .name("name")
                .email("email")
                .password("password")
                .build();

        //when
        mockMvc.perform(patch("/user/1")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(userService).update(eq(1L),any(UserRequest.class));
    }

    @Test
    @DisplayName("update_Invalid_Not_Existed_User")
    public void updateWithNotExistedUser() throws Exception{
        //given
        UserRequest request = UserRequest.builder()
                .name("name")
                .password("password")
                .build();

        //when
        mockMvc.perform(patch("/user/1")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 입력으로 에러가 발생."))
                .andExpect(jsonPath("$.validation.email").value("이메일을 입력하세요."))
                .andDo(print());

        //then
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("delete")
    public void deleteWithExistedUser() throws Exception{
        //when
        mockMvc.perform(delete("/user/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        //then
        verify(userService).delete(eq(1L));
    }

    @Test
    @DisplayName("삭제 실패")
    public void deleteWithNotExistedUser() throws Exception{
        //when
        mockMvc.perform(delete("/user/1000")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        //then
        verify(userService).delete(eq(1000L));
        assertThat(new User().getId()).isNull();
    }

}
