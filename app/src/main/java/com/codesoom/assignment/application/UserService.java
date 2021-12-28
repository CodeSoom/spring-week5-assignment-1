package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;

    public UserService(
            Mapper dozerMapper,
            UserRepository userRepository) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(long id) {
        return findUser(id);
    }

    private User findUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserData userData) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user = mapper.map(userData, User.class);

        return userRepository.save(user);
    }

    public User updateUser(long id, UserData userData) {
        User user = findUser(id);

        user.changeWith(mapper.map(userData, User.class));

        return user;
    }

    public User deleteUser(long id) {
        User user = findUser(id);
        userRepository.delete(user);

        return user;
    }
}
