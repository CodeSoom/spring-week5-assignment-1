package com.codesoom.assignment.application;

import com.codesoom.assignment.ResourceNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserCommandService {

    private UserRepository repository;

    public UserCommandService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user){
        return repository.save(user);
    }

    public User update(User beforeUser , User user){
        return beforeUser.update(user);
    }

    public User delete(User user){
        repository.delete(user);
        return user;
    }
}
