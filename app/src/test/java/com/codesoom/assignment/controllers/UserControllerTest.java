package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserUpdateInfoData;
import com.codesoom.assignment.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends UserControllerTestContext {

    @Nested
    @DisplayName("PATCH /users/{id} 요청은")
    class Describe_update {

        private final UserUpdateInfoData userUpdateInfoData =
                UserUpdateInfoData.builder()
                        .name("배추김치")
                        .password("1234")
                        .build();

        @Nested
        @DisplayName("올바른 요청 값들이 오면")
        class Context_when_valid_values {
            @BeforeEach
            void setUp() {
                given(userService.updateInfo(eq(1L), any(UserUpdateInfoData.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            UserUpdateInfoData data = invocation.getArgument(1);
                            return User.builder()
                                    .id(id)
                                    .name(data.getName())
                                    .password(data.getPassword())
                                    .build();
                        });

            }

            @Test
            @DisplayName("변경된 user를 응답한다.")
            void it_responses_updated_user() throws Exception {
                mockMvc.perform(
                                patch("/users/{id}", 1L)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(toJson(userUpdateInfoData))
                        )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(userUpdateInfoData.getName()))
                        .andExpect(jsonPath("$.password").value(userUpdateInfoData.getPassword()));
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않으면")
        class Context_when_not_existed_id {
            private final Long invalidId = 100L;

            @BeforeEach
            void setUp() {
                given(userService.updateInfo(eq(invalidId), any(UserUpdateInfoData.class)))
                        .willThrow(new UserNotFoundException(invalidId));
            }

            @DisplayName("404 status를 응답한다.")
            @Test
            void it_responses_404_status() throws Exception {
                mockMvc.perform(
                        patch("/users/{id}", invalidId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(userUpdateInfoData))
                ).andExpect(status().isNotFound());

            }
        }

        @Nested
        @DisplayName("유효하지 않는 요청 데이터가 오면")
        class Context_when_invalid_request_data {

            private final UserUpdateInfoData invalidData =
                    UserUpdateInfoData.builder()
                            .name("")
                            .password("")
                            .build();

            @DisplayName("400 status를 응답한다.")
            @Test
            void it_responses_400_status() throws Exception {
                mockMvc.perform(patch("/users/{id}", 1L)
                                .content(toJson(invalidData))
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isBadRequest());
            }
        }

    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청은")
    class Describe_delete {

        @Nested
        @DisplayName("id가 존재하면")
        class Context_when_existed_id {
           private final Long validId = 1L;

            @Test
            @DisplayName("204 status를 응답한다.")
            void it_responses_204_status() throws Exception {
                mockMvc.perform(delete("/users/{id}", validId))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않으면")
        class Context_when_not_existed_id {
            private final Long invalidId = 100L;

            @BeforeEach
            void setUp() {
                doThrow(new UserNotFoundException(invalidId))
                        .when(userService).deleteUser(invalidId);
            }

            @DisplayName("404 status를 응답한다.")
            @Test
            void it_responses_404_status() throws Exception {
                mockMvc.perform(delete("/users/{id}", invalidId))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
