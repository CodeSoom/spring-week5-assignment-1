package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDeleteService {
    private final JpaUserRepository userRepository;

    public UserDeleteService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        userRepository.delete(user);
    }
}
