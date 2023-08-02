package com.codesoom.assignment.application.user;

import com.codesoom.assignment.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDeleter {
    private final UserRepository userRepository;

    public UserDeleter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(Long id) {

    }
}
