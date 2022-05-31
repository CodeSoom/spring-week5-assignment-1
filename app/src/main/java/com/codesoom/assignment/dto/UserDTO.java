package com.codesoom.assignment.dto;

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
		@Mapping("name")
		private String name;
		@Mapping("email")
		private String email;
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
		private String name;
		@Mapping("email")
		private String email;
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
		private String name;
		@Mapping("email")
		private String email;
		@Mapping("password")
		private String password;
	}
}
