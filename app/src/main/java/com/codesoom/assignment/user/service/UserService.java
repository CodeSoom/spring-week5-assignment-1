package com.codesoom.assignment.user.service;

import com.codesoom.assignment.common.exceptions.UserNotFoundException;
import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import com.codesoom.assignment.user.dto.UserCreateRequest;
import com.codesoom.assignment.user.dto.UserResponse;
import com.codesoom.assignment.user.dto.UserUpdateRequest;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public UserResponse getUser(Long id) {
        User foundUser = findUser(id);

        return mapper.map(foundUser, UserResponse.class);
    }

    public UserResponse createUser(UserCreateRequest createRequest) {
        User savedUser = userRepository.save(mapper.map(createRequest, User.class));

        return mapper.map(savedUser, UserResponse.class);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest updateRequest) {
        User user = findUser(id);
        User updatedUser = user.changeWith(mapper.map(updateRequest, User.class));

        return mapper.map(updatedUser, UserResponse.class);
    }

    public void deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 회원 id가 주어졌으므로 회원을 찾을 수 없습니다. 문제의 id = " + id));
    }

}
