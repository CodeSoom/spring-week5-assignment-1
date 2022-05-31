package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	@Getter
	@NoArgsConstructor
	public static class CreateUser {
		@Mapping("name")
		private String name;
		@Mapping("email")
		private String email;
		@Mapping("password")
		private String password;

		public CreateUser(String name, String email, String password) {
			this.name = name;
			this. email = email;
			this.password = password;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class Response{
		@Mapping("id")
		private int id;
		@Mapping("name")
		private String name;
		@Mapping("email")
		private String email;
		@Mapping("password")
		private String password;

		public Response(int id, String name, String email, String password) {
			this.id = id;
			this.name = name;
			this.email = email;
			this.password = password;
		}
	}

	@Getter
	public static class UpdateUser {
		@Mapping("name")
		private String name;
		@Mapping("email")
		private String email;
		@Mapping("password")
		private String password;

		public UpdateUser(String name, String email, String password) {
			this.name = name;
			this.email = email;
			this.password = password;
		}
	}
}
