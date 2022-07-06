package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateService {
    private final JpaUserRepository userRepository;

    public UserUpdateService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(Long id, String name, String email, String password) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }
}
