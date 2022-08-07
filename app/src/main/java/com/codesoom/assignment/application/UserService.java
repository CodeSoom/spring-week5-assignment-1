package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final Mapper mapper;

    public UserService(UserRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public User createUser(UserData userData) {
        User user = mapper.map(userData, User.class);
        return repository.save(user);
    }

    public User updateUser(Long id, UserData toUserData) {
        User user = findUser(id);
        User toUser = mapper.map(toUserData, User.class);
        user.change(toUser);
        return user;
    }

    public void deleteUserById(Long id) {
        User user = findUser(id);

        repository.delete(user);
    }

    private User findUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
