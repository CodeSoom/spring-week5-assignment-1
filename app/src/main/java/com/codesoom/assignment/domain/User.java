package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.codesoom.assignment.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private int id;

	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String password;

	public void update(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
