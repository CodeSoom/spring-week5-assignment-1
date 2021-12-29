package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.exception.UserNotFoundException;
import com.codesoom.assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserData userData) {
        User user = User.builder()
                .name(userData.getName())
                .password(userData.getPassword())
                .email(userData.getEmail())
                .build();

        return userRepository.save(user);
    }

    public User updateUser(Long targetId, UserData source) {
        User user = getUser(targetId);

        user.change(source.getName(),
                source.getPassword(),
                source.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Long targetId) {
        try {
            userRepository.deleteById(targetId);
        } catch (Exception e) {
            throw new UserNotFoundException(targetId);
        }
    }
}
