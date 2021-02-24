package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
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

    public User createUser(User source) {
        return userRepository.save(source);
    }

    public User updateUser(Long id, User source) {
        User user = getUser(id);

        user.setName(source.getName());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());

        return user;
    }

    public User deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);

        return user;
    }
}
