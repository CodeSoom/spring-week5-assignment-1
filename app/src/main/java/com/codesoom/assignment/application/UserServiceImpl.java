package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDTO;
import com.codesoom.assignment.dto.UserResponse;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserServiceInterface {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserResponse createUser(UserDTO.CreateUser source) {
        return mapper.map(userRepository.save(mapper.map(source, User.class)), UserResponse.class);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(int id) {
        return mapper.map(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)),
                UserResponse.class);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

    }

    public UserResponse updateUsers(int id, UserDTO.UpdateUser source) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.update(source.getName(), source.getEmail(), source.getPassword());
        return mapper.map(user, UserResponse.class);
    }
}
