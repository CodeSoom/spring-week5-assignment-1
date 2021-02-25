package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserData userData) {
        User user = userData.toEntity();

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserData userData) {
        User user = getUser(id);

        user.update(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

        return user;
    }

    public User deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);

        return user;
    }
}
