package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;
	private Mapper dozerMapper;

	public UserService(UserRepository userRepository, Mapper dozerMapper) {
		this.userRepository = userRepository;
		this.dozerMapper = dozerMapper;
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

	public UserData updateDetail(Long id, UserData userData) {
		return userData;
	}

	public void delete(Long id) {

	}
}
