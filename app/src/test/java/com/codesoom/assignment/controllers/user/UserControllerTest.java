package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.domain.user.UserRepository;
import com.codesoom.assignment.dto.user.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserController 클래스")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메서드는 {
        private final String TEST_NAME = "testName";
        private final String TEST_EMAIL = "test@Email";
        private final String TEST_PASSWORD = "testPassword";
        private UserData USER_REQUEST;

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저_정보가_요청이_오면 {

            @BeforeEach
            void setUp() {
                USER_REQUEST = UserData.builder()
                        .name(TEST_NAME)
                        .email(TEST_EMAIL)
                        .password(TEST_PASSWORD)
                        .build();
            }

            @DisplayName("해당_유저정보를_저장_후_저장한_유저정보를_리턴한다")
            @Test
            void it_saves_and_returns_user() throws Exception {
                String jsonString = objectMapper.writeValueAsString(USER_REQUEST);

                mockMvc.perform(post("/users")
                                .contentType("application/json")
                                .content(jsonString))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").value("testName"))
                        .andExpect(jsonPath("email").value("test@Email"))
                        .andExpect(jsonPath("password").value("testPassword"))
                        .andDo(print());
            }
        }
    }
}
