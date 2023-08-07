package com.codesoom.assignment.application.user;

import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import com.codesoom.assignment.dto.user.UserData;
import org.springframework.stereotype.Service;

@Service
public class UserCreator {

    private final UserRepository userRepository;

    public UserCreator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserData userRequest) {
        return userRepository.save(userRequest.toUser());
    }
}
