package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.codesoom.assignment.dto.UserUpdateRequest;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    public List<User> getUsers() {
        return userRepository.findAll();
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

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 회원 id가 주어졌으므로 회원을 찾을 수 없습니다. 문제의 id = " + id));
    }

    public void deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);
    }

}
