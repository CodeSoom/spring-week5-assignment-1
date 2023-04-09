package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.web.shop.user.dto.UserRegistrationData;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;


    public User registerUser(UserRegistrationData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }
}
