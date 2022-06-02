package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.UserNotFoundException;
import com.codesoom.assignment.application.interfaces.*;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCrudService implements UserShowService {
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

}


