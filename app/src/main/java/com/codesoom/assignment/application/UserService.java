package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User source) {
        return null;
    }

    public User updateUser(Long id, User source) {
        return null;
    }

    public User deleteUser(Long id) {
        return null;
    }
}
