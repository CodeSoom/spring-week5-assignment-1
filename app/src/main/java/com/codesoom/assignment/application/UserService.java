package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserModificationData;
import com.codesoom.assignment.dto.UserRegistrationData;
import com.codesoom.assignment.exception.NotFoundIdException;
import com.codesoom.assignment.exception.UserEmailDuplcationException;
import com.codesoom.assignment.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(UserRegistrationData userData) {
        //TODO
        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new UserEmailDuplcationException(userData.getEmail());
        }
        User user = User.builder()
                .email(userData.getEmail())
                .name(userData.getName())
                .password(userData.getPassword())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserModificationData userModificationData) {
        User user = findUser(id);
        user.change(userModificationData);
        return user;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new NotFoundIdException(id));
    }

    public User delete(Long id) {
        User user = findUser(id);
        if(user.isDeleted()){
            throw new UserNotFoundException(id);
        }
        user.destory();
        return user;
    }
}
