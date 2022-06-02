package com.codesoom.assignment.controllers;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.Utf8WebTest;
import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Utf8WebTest
@WebMvcTest(UserController.class)
class UserControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1L)
                .name("caoyu")
                .email("choyumin01@gmail.com")
                .password("1234!@#$")
                .build();

        given(userService.createUser(any(UserCreateData.class))).willReturn(user);

        given(userService.updateUser(eq(1L), any(UserUpdateData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    UserUpdateData userUpdateData = invocation.getArgument(1);
                    return User.builder()
                            .id(id)
                            .name(userUpdateData.getName())
                            .email(userUpdateData.getEmail())
                            .password(userUpdateData.getPassword())
                            .build();
                });

        given(userService.updateUser(eq(1000L), any(UserUpdateData.class)))
                .willThrow(new UserNotFoundException(1000L));

        given(userService.deleteUser(1000L))
                .willThrow(new UserNotFoundException(1000L));
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"caoyu\",\"email\":\"choyumin01@gmail.com\",\"password\":\"1234!@#$\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("caoyu")));

        verify(userService).createUser(any(UserCreateData.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"\",\"password\":\"\"}")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithExistedUser() throws Exception {
        mockMvc.perform(
                patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"caoyu-dev\",\"email\":\"choyumin01@gmail.com\",\"password\":\"!@#$1234\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("caoyu-dev")));

        verify(userService).updateUser(eq(1L), any(UserUpdateData.class));
    }

    @Test
    void updateWithNotExistedUser() throws Exception {
        mockMvc.perform(
                patch("/users/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"caoyu-dev\",\"email\":\"choyumin01@gmail.com\",\"password\":\"!@#$1234\"}")
                )
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService).updateUser(eq(1000L), any(UserUpdateData.class));
    }

    @Test
    void deleteWithExistedUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteWithNotExistedUser() throws Exception {
        mockMvc.perform(delete("/users/1000"))
                .andExpect(status().isNotFound());

        verify(userService).deleteUser(1000L);
    }

}
