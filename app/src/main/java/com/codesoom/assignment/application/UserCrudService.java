package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.UserNotFoundException;
import com.codesoom.assignment.application.interfaces.*;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCrudService implements UserShowService, UserCreateService, UserUpdateService {
    private final UserRepository repository;

    public UserCrudService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> showAll() {
        return repository.findAll();
    }

    @Override
    public User showById(Long id) {
        return repository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User create(User user) {
        User userSaving = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return repository.save(userSaving);
    }

    @Override
    public User update(Long id, User user) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        User userUpdating = User.builder()
                .id(id)
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return repository.save(userUpdating);
    }
}


