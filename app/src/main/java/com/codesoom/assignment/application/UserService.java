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
 * 서비스를 이용하는 사용자들의 정보를 전달받아 처리한다.
 */
@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(Mapper dozerMapper, UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return userRepository.save(user);
    }

    public User updateUser(UserData userData) {
        User user = getUser(userData.getId());

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    public User deleteUser(Long id) {
        final User user = getUser(id);

        userRepository.delete(user);

        return user;
    }
}
