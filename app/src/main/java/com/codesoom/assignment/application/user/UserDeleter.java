package com.codesoom.assignment.application.user;

import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDeleter {
    private final UserRepository userRepository;

    private final UserReader userReader;

    public UserDeleter(UserRepository userRepository, UserReader userReader) {
        this.userRepository = userRepository;
        this.userReader = userReader;
    }
    public void deleteUser(Long id) {
        User user = userReader.getUser(id);
        userRepository.deleteById(user.getId());
    }
}
