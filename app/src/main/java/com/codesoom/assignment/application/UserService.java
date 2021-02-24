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

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        return null;
    }

    public UserResponse deleteUser(Long id) {
        return null;
    }
}
