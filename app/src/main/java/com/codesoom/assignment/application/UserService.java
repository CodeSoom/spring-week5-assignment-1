package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public User signUp(UserData userData) {
        User user = User.builder()
                .email(userData.getEmail())
                .name(userData.getName())
                .password(userData.getPassword())
                .build();

        return userRepository.save(user);
    }
}
