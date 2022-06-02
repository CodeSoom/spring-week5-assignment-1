package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;

import com.codesoom.assignment.global.CustomNotBlank;
import com.github.dozermapper.core.Mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	@Getter
	@Builder
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateUser {
		@CustomNotBlank
		@Mapping("name")
		private String name;
		@CustomNotBlank
		@Mapping("email")
		private String email;
		@CustomNotBlank
		@Mapping("password")
		private String password;
	}

	@Getter
	@Builder
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response{
		@Mapping("id")
		private int id;
		@Mapping("name")
		@CustomNotBlank
		private String name;
		@CustomNotBlank
		@Mapping("email")
		private String email;
		@CustomNotBlank
		@Mapping("password")
		private String password;
	}

	@Getter
	@Builder
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UpdateUser {
		@Mapping("name")
		@CustomNotBlank
		private String name;

		@Mapping("email")
		@CustomNotBlank
		private String email;

		@CustomNotBlank
		@Mapping("password")
		private String password;
	}
}
