package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	private void setUp() {
		objectMapper = new ObjectMapper();
	}


	@Test
	public void create() throws Exception {
		UserData user = UserData.builder()
				.name("지니")
				.email("test@gmail.com")
				.password("1234")
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/users")
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJsonString(user)))
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString("지니")));
	}

	@Test
	public void getAll() throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("지니")));
	}

	@Test
	public void getDetailWithExsistedId() throws Exception {
		mockMvc.perform(get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("지니")));
	}

	@Test
	public void getDetailWithNotExsistedId() throws Exception {
		mockMvc.perform(get("/users/100"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateWithValidData() throws Exception {
		UserData user = UserData.builder()
				.name("코딩쟁이")
				.email("who@gmail.com")
				.password("1000")
				.build();

		mockMvc.perform(patch("/users/1")
						.accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJsonString(user)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("코딩쟁이")));
	}

	@Test
	public void updateWithInValidData() throws Exception {
		UserData user = UserData.builder()
				.name("")
				.email("")
				.password("")
				.build();

		mockMvc.perform(patch("/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJsonString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void remove() throws Exception {
		mockMvc.perform(delete("/users/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getJsonStringTest() throws JsonProcessingException {
		UserData user = UserData.builder()
				.id(1L)
				.name("지니")
				.email("test@gmail.com")
				.password("1234")
				.build();

		assertThat(getJsonString(user)).isEqualTo("{\"id\":1,\"name\":\"지니\",\"email\":\"test@gmail.com\",\"password\":\"1234\"}");
	}

	public String getJsonString(UserData userData) throws JsonProcessingException {
		return objectMapper.writeValueAsString(userData);
	}

}
