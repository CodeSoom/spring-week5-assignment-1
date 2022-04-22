package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserCreateData;
import com.codesoom.assignment.dto.UserUpdateData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreateData userData) {
        User user = User.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserUpdateData userData) {
        User user = findUser(id);

        user.update(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

        return user;
    }

    public User deleteUser(Long id) {
        User product = findUser(id);

        userRepository.delete(product);

        return product;
    }

    private User findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    return new UserNotFoundException(id);
                });
        return user;
    }
}
