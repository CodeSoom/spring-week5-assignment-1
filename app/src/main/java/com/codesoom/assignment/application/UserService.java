package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(UserData userData) {
        final User user = User.builder()
                .name(userData.getName())
                .password(userData.getPassword())
                .email(userData.getEmail())
                .build();
        return repository.save(user);
    }
}
