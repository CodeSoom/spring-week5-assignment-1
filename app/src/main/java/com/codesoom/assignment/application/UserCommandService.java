package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserRequest;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserCommandService {
    private final UserRepository userRepository;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public UserCommandService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        User findUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        User user = mapper.map(userRequest, User.class);

        findUser.change(user.getEmail(), user.getName(), user.getPassword());
        return userRepository.save(findUser);
    }

    public Long deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(id);
        return id;
    }

    public List<Long> deleteUsers(List<Long> ids) {
        Iterable<User> users = userRepository.findAllById(ids);

        Set<Long> userSet = new HashSet<>();
        users.forEach(user -> userSet.add(user.getId()));

        ids.forEach(id -> {
            if (!userSet.contains(id)) {
                throw new UserNotFoundException();
            }
        });

        userRepository.deleteAll(users);
        return ids;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
