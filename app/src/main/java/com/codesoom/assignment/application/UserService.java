package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;
	private Mapper mapper;

	public UserService(UserRepository userRepository, Mapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User getDetail(Long id) {

		return null;
	}

	public UserData create(UserData user) {
		return user;
	}

	public User updateDetail(Long id, UserData userData) {
		return null;
	}

	public void delete(Long id) {

	}
}
