package com.codesoom.assignment.controllers.user;

import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import com.codesoom.assignment.dto.user.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("UserController 클래스")
class UserControllerTest {

    private final String TEST_NAME = "testName";
    private final String TEST_EMAIL = "test@Email";
    private final String TEST_PASSWORD = "testPassword";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;
    private UserData USER_REQUEST;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메서드는 {


        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저_정보_요청이_오면 {

            @BeforeEach
            void setUp() {
                USER_REQUEST = UserData.builder()
                        .name(TEST_NAME)
                        .email(TEST_EMAIL)
                        .password(TEST_PASSWORD)
                        .build();
            }

            @DisplayName("해당_유저정보를_등록_후_저장한_유저정보를_리턴한다")
            @Test
            void it_saves_and_returns_user() throws Exception {
                String jsonString = objectMapper.writeValueAsString(USER_REQUEST);

                mockMvc.perform(patch("/users")
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

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class update_메서드 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유저_수정_요청이_오면 {
            private Long id;

            @BeforeEach
            void setUp() {
                userRepository.deleteAll();
                User user = User.builder()
                        .name(TEST_NAME)
                        .email(TEST_EMAIL)
                        .password(TEST_PASSWORD)
                        .build();

                id = userRepository.save(user).getId();
            }

            @DisplayName("해당_유저정보를_수정_후_수정한_유저정보를_리턴한다")
            @Test
            void it_updates_and_returns_user() throws Exception {
                UserData request = UserData.builder()
                        .name("newName")
                        .email("newEmail")
                        .password("newPassword")
                        .build();

                String jsonString = objectMapper.writeValueAsString(request);

                mockMvc.perform(post("/users/" + id)
                                .contentType("application/json")
                                .content(jsonString))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("name").value("newName"))
                        .andExpect(jsonPath("email").value("newEmail"))
                        .andExpect(jsonPath("password").value("newPassword"))
                        .andDo(print());

            }

        }
    }
}
