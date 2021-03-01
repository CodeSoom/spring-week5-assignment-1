package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 회원 정보와 관련된 비지니스 로직을 처리합니다.
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return findUser(id);
    }

    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }


    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.changeWith(mapper.map(userData, User.class));

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

    public User putUser(Long id, UserData userData) {
        User user = findUser(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }
}
