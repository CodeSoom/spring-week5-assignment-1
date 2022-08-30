package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll(){
        return null;
    }

    public User save(User user){
        return null;
    }

    public User update(Long id , User user){
        return null;
    }

    public User delete(Long id){
        // TODO: 미구현
        return null;
    }
}
