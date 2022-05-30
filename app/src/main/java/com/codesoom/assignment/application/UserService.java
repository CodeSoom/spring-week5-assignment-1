package com.codesoom.assignment.application;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
