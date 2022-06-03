package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, Mapper dozerMapper) {
        this.userRepository = userRepository;
        this.mapper = dozerMapper;
    }

    public User create(UserData userData) {
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    public User update(Long id, UserData userData) {
        User user = findUser(id);

        user.update(mapper.map(userData, User.class));

        return user;
    }

    public User delete(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
