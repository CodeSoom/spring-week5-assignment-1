package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.CreateUserData;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCreator {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public User create(CreateUserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }
}
