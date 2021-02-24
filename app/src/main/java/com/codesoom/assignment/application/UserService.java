package com.codesoom.assignment.application;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.github.dozermapper.core.Mapper;

@Service
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = new User()
            .builder()
            .name(userRequest.getName())
            .email(userRequest.getEmail())
            .password(userRequest.getPassword())
            .build();

        User response = userRepository.save(user);

        return new UserResponse()
            .builder()
            .id(response.getId())
            .name(response.getName())
            .email(response.getEmail())
            .password(response.getPassword())
            .build();
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        return null;
    }

    public UserResponse deleteUser(Long id) {
        return null;
    }
}
