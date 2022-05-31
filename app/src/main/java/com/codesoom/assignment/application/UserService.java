package com.codesoom.assignment.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDTO.Response createUser(UserDTO.CreateUser source) {
		return mapper.map(userRepository.save(mapper.map(source, User.class)), UserDTO.Response.class);
	}

	public UserDTO.Response getUser(int id) {
		return mapper.map(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)),
			UserDTO.Response.class);
	}

	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	public List<UserDTO.Response> getUsers() {
		return userRepository.findAll().stream().map(user -> mapper.map(user, UserDTO.Response.class))
			.collect(Collectors.toList());

	}

	public UserDTO.Response updateUsers(int id, UserDTO.UpdateUser source) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(1));
		user.update(source.getName(), source.getEmail(), source.getPassword());
		return mapper.map(user, UserDTO.Response.class);
	}
}
