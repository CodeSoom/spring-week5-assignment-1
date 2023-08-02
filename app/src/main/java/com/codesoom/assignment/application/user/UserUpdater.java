package com.codesoom.assignment.application.user;

import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import com.codesoom.assignment.dto.user.UserRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserUpdater {

    private UserRepository userRepository;

    private UserReader userReader;

    public UserUpdater(UserRepository userRepository, UserReader userReader) {
        this.userRepository = userRepository;
        this.userReader = userReader;
    }

    @Transactional
    public User updateUser(Long id, UserRequest userRequest) {
        User user = userReader.getUser(id);
        user.change(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());

        return userRepository.save(user);

    }
}
