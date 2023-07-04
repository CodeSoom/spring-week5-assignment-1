package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Mapping("id")
	private Long id;
	@Mapping("name")
	private String name;
	@Mapping("email")
	private String email;
	@Mapping("password")
	private String password;

	@Builder
	public User(Long id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public void update(UserData source) {
		this.name = source.getName();
		this.email = source.getEmail();
		this.password = source.getPassword();
	}
}
