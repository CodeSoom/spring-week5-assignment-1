package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreateData userCreateData) {
        User user = User.builder()
                .name(userCreateData.getName())
                .email(userCreateData.getEmail())
                .password(userCreateData.getPassword())
                .build();

        return userRepository.save(user);
    }

}
