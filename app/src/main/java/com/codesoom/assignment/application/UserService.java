package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return null;
    }

    public User updateUser(Long targetId, User source) {
        return null;
    }

    public User deleteUser(Long targetId) {
        return null;
    }
}
