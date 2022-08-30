package com.codesoom.assignment.application;

import com.codesoom.assignment.ResourceNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return repository.findAll();
    }

    @Transactional
    public User save(User user){
        return repository.save(user);
    }

    @Transactional
    public User update(Long id , User user){
        User beforeUser = findUser(id);
        return beforeUser.update(user);
    }

    @Transactional
    public User delete(Long id){
        User user = findUser(id);
        repository.delete(user);
        return user;
    }

    private User findUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
