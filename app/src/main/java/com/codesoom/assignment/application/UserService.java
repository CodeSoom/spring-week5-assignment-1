package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final Mapper mapper;

	public UserService(UserRepository userRepository, Mapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User getDetail(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public User create(UserData source) {
		User user = mapper.map(source, User.class);

		return userRepository.save(user);
	}

	public User updateDetail(Long id, UserData userData) {
		User user = getDetail(id);
		user.update(userData);

		return user;
	}

	public void delete(Long id) {
		User user = getDetail(id);
		userRepository.delete(user);
	}
}
