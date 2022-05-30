package com.codesoom.assignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	public static class CreateUser {
		private String name;
		private String email;
		private String password;

		public CreateUser(String name, String email, String password) {
			this.name = name;
			this. email = email;
			this.password = password;
		}
	}
	@Getter
	public static class Response{
		private int id;
		private String name;
		private String email;
		private String password;

		public Response(int id, String name, String email, String password) {
			this.id = id;
			this.name = name;
			this.email = email;
			this.password = password;
		}
	}
}
