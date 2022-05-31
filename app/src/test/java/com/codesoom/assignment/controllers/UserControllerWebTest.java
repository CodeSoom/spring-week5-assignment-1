package com.codesoom.assignment.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codesoom.assignment.application.UserService;
import com.codesoom.assignment.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerWebTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private UserService userService;

	@BeforeEach
	void setUp() {
		UserDTO.Response response = new UserDTO.Response(1, "create name test",
			"create email test", "create password test");
		given(userService.createUser(any(UserDTO.CreateUser.class)))
			.willReturn(response);

		UserDTO.UpdateUser updateUser = new UserDTO.UpdateUser("update name test", "update email test",
			"update password test");
		given(userService.updateUsers(eq(1), any(UserDTO.UpdateUser.class)))
			.will(invocation -> {
				UserDTO.UpdateUser source = invocation.getArgument(0);
				return new UserDTO.Response(1, source.getName(), source.getEmail(), source.getPassword());
			});

		//TODO 아래의 코드에서 createUser 의 attribute 가 null 로 뽑히는데 이유가 무엇인지 알아내기.

		// given(userService.createUser(any(UserDTO.CreateUser.class)))
		// 	.will(invocation -> {
		// 		System.out.println("==================");
		// 		UserDTO.CreateUser createUser = invocation.getArgument(0);
		// 		System.out.println(createUser.getEmail());
		// 		System.out.println("==================");
		//
		// 		UserDTO.CreateUser source = invocation.getArgument(0);
		// 		UserDTO.Response response = new UserDTO.Response(1, source.getName(), source.getEmail(),
		// 			source.getPassword());
		// 		return response;
		// 	});
	}

	@Test
	void createUser() throws Exception {
		mockMvc.perform(
				post("/users")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						"{\"name\":\"create name test\",\"email\":\"create email test\",\"password\":\"create password test\"}"))
			.andExpect(status().isCreated())
			.andExpect(content().string(containsString("create email test")));
		verify(userService).createUser(any(UserDTO.CreateUser.class));
	}

	@Test
	void updateUser() throws Exception {
		mockMvc.perform(
				put("/users")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						"{\"name\":\"update name test\",\"email\":\"update email test\",\"password\":\"update password test\"}"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("update email test")));
		verify(userService).updateUsers(eq(1), any(UserDTO.UpdateUser.class));
	}
}
