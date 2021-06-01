package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserDto;
import com.codesoom.assignment.exception.NotFoundUserException;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private Mapper mapper;

    public UserService(Mapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public User createUser(UserDto source) {
        User user = mapper.map(source, User.class);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDto source) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id));
        user.change(mapper.map(source, User.class));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
