package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequestDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 비지니스 로직을 제공합니다.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserRequestDto userRequestDto) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userRequestDto, User.class);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserRequestDto userRequestDto) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userRequestDto, User.class);
        return userRepository.save(user);
    }
}
