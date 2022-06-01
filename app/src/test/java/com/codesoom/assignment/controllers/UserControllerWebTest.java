package com.codesoom.assignment.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerWebTest {
	private static final UserDTO.Response response1 = new UserDTO.Response(1, "name test 1",
		"email test 1", "password test 1");
	private static final UserDTO.Response response2 = new UserDTO.Response(2, "name test 2",
		"email test 2", "password test 2");
	private static final List<UserDTO.Response> userDTOs = Arrays.asList(response1, response2);

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Nested
	@DisplayName("POST /users URL 은")
	class createUser {
		@Nested
		@DisplayName("유효한 유저 Attribute 가 주어지면")
		class WithValidAttribute {
			@Test
			@DisplayName("상태코드 200 과 생성된 유저 정보를 응답한다.")
			void createUserWithValidAttribute() throws Exception {
				given(userService.createUser(any(UserDTO.CreateUser.class)))
					.will(invocation -> {
						UserDTO.CreateUser source = invocation.getArgument(0);
						return new UserDTO.Response(1, source.getName(), source.getEmail(), source.getPassword());
					});

				String source = objectMapper.writeValueAsString(new UserDTO.CreateUser("create name test",
					"create email test", "create password test"));

				mockMvc.perform(
						post("/users")
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON)
							.content(source))
					.andExpect(status().isCreated())
					.andExpect(content().string(containsString("create email test")));

				verify(userService).createUser(any(UserDTO.CreateUser.class));
			}
		}

		@Nested
		@DisplayName("유효하지 않은 유저 Attribute 가 주어지면")
		class WithInValidAttribute {
			@Test
			@DisplayName("상태코드 400 을 응답한다.")
			void createUserWithInValidAttribute() throws Exception {
				given(userService.createUser(any(UserDTO.CreateUser.class)))
					.will(invocation -> {
						UserDTO.CreateUser source = invocation.getArgument(0);
						return new UserDTO.Response(1, source.getName(), source.getEmail(),
							source.getPassword());
					});

				String source = objectMapper.writeValueAsString(new UserDTO.CreateUser("",
					"", ""));

				mockMvc.perform(
						post("/users")
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON)
							.content(source))
					.andExpect(status().isBadRequest());
			}
		}
	}

	@Nested
	@DisplayName("PATCH /user/{id} URL 은")
	class updateUser {
		@Nested
		@DisplayName("유효한 id 와 업데이트 유저 정보가 주어지면")
		class WithValidId {
			@Test
			@DisplayName("상태코드 200, 업데이트 된 유저 정보를 응답한다.")
			void updateUserWithValidId() throws Exception {
				given(userService.updateUsers(eq(1), any(UserDTO.UpdateUser.class)))
					.will(invocation -> {
						int id = invocation.getArgument(0);
						UserDTO.UpdateUser source = invocation.getArgument(1);
						return UserDTO.Response.builder()
							.id(id)
							.name(source.getName())
							.email(source.getEmail())
							.password(source.getPassword())
							.build();
					});

				String source = objectMapper.writeValueAsString(
					new UserDTO.UpdateUser("update name test", "update email test", "update password test"));

				mockMvc.perform(
						patch("/users/{id}", 1)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.contentType(MediaType.APPLICATION_JSON)
							.content(source))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("update email test")));

				verify(userService).updateUsers(eq(1), any(UserDTO.UpdateUser.class));
			}
		}

		@Nested
		@DisplayName("유효하지 않은 id 와 업데이트 유저 정보가 주어지면")
		class WithInValidId {
			@Test
			@DisplayName("상태코드 404 를 응답한다.")
			void updateUserWithInValidId() throws Exception {
				given(userService.updateUsers(eq(1000), any(UserDTO.UpdateUser.class))).willThrow(
					new UserNotFoundException(1000));

				String source = objectMapper.writeValueAsString(
					new UserDTO.UpdateUser("update name test", "update email test", "update password test"));

				mockMvc.perform(
						patch("/users/{id}", 1000)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.contentType(MediaType.APPLICATION_JSON)
							.content(source))
					.andExpect(status().isNotFound());

				verify(userService).updateUsers(eq(1000), any(UserDTO.UpdateUser.class));
			}
		}

		@Nested
		@DisplayName("id 와 유효하지 않은 업데이트 유저 정보가 주어지면")
		class WithInValidAttribute {
			@Test
			@DisplayName("상태코드 400 를 응답한다.")
			void updateUserWithInValidId() throws Exception {
				String source = objectMapper.writeValueAsString(
					new UserDTO.UpdateUser("", "", ""));

				mockMvc.perform(
						patch("/users/{id}", 1)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.contentType(MediaType.APPLICATION_JSON)
							.content(source))
					.andExpect(status().isBadRequest());
			}
		}
	}

	@Nested
	@DisplayName("GET /users/{id} URL 은")
	class GetUserTest {
		@Nested
		@DisplayName("유효한 id 가 주어지면")
		class WithValidId {
			@Test
			@DisplayName("상태코드 200 을 응답한다.")
			void getUserWithValidId() throws Exception {
				given(userService.getUser(eq(1))).willReturn(response1);

				mockMvc.perform(get("/users/{id}", 1))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("name test 1")));

				verify(userService).getUser(1);
			}
		}

		@Nested
		@DisplayName("유효하지 않은 id 가 주어지면")
		class WithInValidId {
			@Test
			@DisplayName("상태코드 404 를 응답한다.")
			void getUserWithInValidId() throws Exception {
				given(userService.getUser(eq(1000))).willThrow(new UserNotFoundException(1000));

				mockMvc.perform(get("/users/{id}", 1000))
					.andExpect(status().isNotFound());

				verify(userService).getUser(1000);
			}
		}
	}

	@Nested
	@DisplayName("GET /users URL 은")
	class GetUsersTest {
		@Test
		@DisplayName("상태코드 200 과 유저 목록을 응답한다.")
		void getUsers() throws Exception {
			given(userService.getUsers()).willReturn(userDTOs);

			mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("email test 2")))
				.andExpect(content().string(containsString("email test 1")));

			verify(userService).getUsers();
		}
	}

	@Nested
	@DisplayName("DELETE /users/{id} URL 은")
	class DeleteUsersTest {
		@Nested
		@DisplayName("유효한 id 가 주어지면")
		class WithValidId {
			@Test
			@DisplayName("상태코드 200 을 응답한다.")
			void deleteUser() throws Exception {
				mockMvc.perform(delete("/users/{id}", 1))
					.andExpect(status().isNoContent());

				verify(userService).deleteUser(1);
			}
		}

		@Nested
		@DisplayName("유효하지 않은 id 가 주어지면")
		class WithInValidId {
			@Test
			@DisplayName("상태코드 404 를 응답한다.")
			void deleteUser() throws Exception {
				willThrow(UserNotFoundException.class).given(userService).deleteUser(eq(1000));

				mockMvc.perform(delete("/users/{id}", 1000))
					.andExpect(status().isNotFound());

				verify(userService).deleteUser(1000);
			}
		}
	}
}
