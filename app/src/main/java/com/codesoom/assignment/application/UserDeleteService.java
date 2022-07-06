package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.infra.JpaUserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDeleteService {
    private final JpaUserRepository userRepository;

    public UserDeleteService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));

        userRepository.delete(user);
    }
}
