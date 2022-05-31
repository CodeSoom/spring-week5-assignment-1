package com.codesoom.assignment.application;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Service
public class UserService {
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDTO.Response createUser(UserDTO.CreateUser source) {
		Mapper mapper = DozerBeanMapperBuilder.buildDefault();
		User user = userRepository.save(mapper.map(source, User.class));
		return new UserDTO.Response(user.getId(), user.getName(), user.getEmail(),
			user.getPassword());
	}
}
