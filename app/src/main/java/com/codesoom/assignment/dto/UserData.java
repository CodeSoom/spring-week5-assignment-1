package com.codesoom.assignment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@ToString
public class UserData {

	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String password;

	@Builder
	public UserData(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
