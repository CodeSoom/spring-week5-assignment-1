package com.codesoom.assignment.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.Mapper;

@Service
@Transactional
public class UserServiceImpl implements UserServiceInterface {
	private final UserRepository userRepository;
	private final Mapper mapper;

	public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	public UserDTO.Response createUser(UserDTO.CreateUser source) {
		return mapper.map(userRepository.save(mapper.map(source, User.class)), UserDTO.Response.class);
	}

	@Transactional(readOnly = true)
	public UserDTO.Response getUser(int id) {
		return mapper.map(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)),
			UserDTO.Response.class);
	}

	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<UserDTO.Response> getUsers() {
		return userRepository.findAll().stream().map(user -> mapper.map(user, UserDTO.Response.class))
			.collect(Collectors.toList());

	}

	public UserDTO.Response updateUsers(int id, UserDTO.UpdateUser source) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		user.update(source.getName(), source.getEmail(), source.getPassword());
		return mapper.map(user, UserDTO.Response.class);
	}
}
