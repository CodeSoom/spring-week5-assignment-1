package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import com.codesoom.assignment.dto.UserResponse;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 사용자에 대한 서비스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        user = userRepository.save(user);
        return mapper.map(user, UserResponse.class);
    }
}
