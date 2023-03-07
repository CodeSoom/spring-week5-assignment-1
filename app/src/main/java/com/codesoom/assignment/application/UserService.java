package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final Mapper dozerMapper;

    public UserService(UserRepository userRepository, Mapper dozerMapper) {
        this.userRepository = userRepository;
        this.dozerMapper = dozerMapper;
    }

    public User createUser(UserData userData) {

        User user = dozerMapper.map(userData, User.class);

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.change(
                userData.getName(),
                userData.getEmail(),
                userData.getPassword()
        );

        return user;
    }

    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
