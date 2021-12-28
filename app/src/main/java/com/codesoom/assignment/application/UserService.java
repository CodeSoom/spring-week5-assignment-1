package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.codesoom.assignment.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long targetId, User source) {
        User user = getUser(targetId);

        user.change(source.getName(),
                source.getPassword(),
                source.getEmail());

        return user;
    }

    public void deleteUser(Long targetId) {
        User user = getUser(targetId);

        userRepository.delete(user);
    }
}
