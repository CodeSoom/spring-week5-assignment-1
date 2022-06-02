package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private User user;

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

    public User updateUser(Long id, UserUpdateData userUpdateData) {
        User user = getUser(id);

        user.update(
                userUpdateData.getName(),
                userUpdateData.getEmail(),
                userUpdateData.getPassword()
        );

        return user;
    }

    public User deleteUser(Long id) {
        User user = getUser(id);

        userRepository.delete(user);

        return user;
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
