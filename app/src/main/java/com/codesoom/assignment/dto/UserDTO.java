package com.codesoom.assignment.dto;

import javax.validation.constraints.NotBlank;

import com.codesoom.assignment.global.PassWordForm;
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
		@NotBlank
		@Mapping("name")
		private String name;
		@NotBlank
		@Mapping("email")
		private String email;
		@PassWordForm
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
		@NotBlank
		private String name;
		@NotBlank
		@Mapping("email")
		private String email;
		@PassWordForm
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
		@NotBlank
		private String name;

		@Mapping("email")
		@NotBlank
		private String email;

		@PassWordForm
		@Mapping("password")
		private String password;
	}
}
