package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {
    private final JpaUserRepository userRepository;

    public UserRegisterService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String password) {
        return userRepository.save(new User(name, email, password));
    }
}
