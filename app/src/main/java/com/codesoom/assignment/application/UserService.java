package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.exception.UserEmailDuplcationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(UserRegistrationData userData){
        //TODO
        if (userRepository.existsByEmail(userData.getEmail())){
            throw new UserEmailDuplcationException(userData.getEmail());
        }
        User user = User.builder()
                .email(userData.getEmail())
                .name(userData.getName())
                .password(userData.getPassword())
                .build();
        return userRepository.save(user);
    }
}
