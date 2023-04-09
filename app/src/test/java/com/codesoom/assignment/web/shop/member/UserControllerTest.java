package com.codesoom.assignment.web.shop.member;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.web.shop.user.UserController;
import com.codesoom.assignment.web.shop.user.dto.UserRegistrationData;
import com.codesoom.assignment.web.shop.user.dto.UserResultData;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void registerUserWithValidAttributes() throws Exception {
        BDDMockito.given(userService.registerUser(any(UserRegistrationData.class))).will(invocation -> {
            UserResultData userData = invocation.getArgument(0);
            return User.builder()
                    .email(userData.getEmail())
                    .build();
        });

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"tester@naver.com\", " +
                                "\"name\" :\"Tester\", " +
                                "\"password\":\"test\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"email\":\"tester@naver.com\"")));

        verify(userService).registerUser(any(UserRegistrationData.class));
    }

    @Test
    void registerUserWithInvalidAttributes() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }
}
