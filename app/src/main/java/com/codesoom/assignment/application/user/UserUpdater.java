package com.codesoom.assignment.application.user;

import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import com.codesoom.assignment.dto.user.UserRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserUpdater {
    private UserRepository userRepository;

    public UserUpdater(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));
        user.change(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }
}
