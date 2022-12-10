package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.infra.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findUser(Long id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User saveUser(UserData userData) {
        User user = User.builder()
                        .name(userData.getName())
                        .email(userData.getEmail())
                        .password(userData.getPassword())
                        .build();
        return repository.save(user);
    }

    public User updateUser(Long id, UserData userData) {
        User user = findUser(id);

        user.changeUser(userData.getName(), userData.getEmail(), userData.getPassword());

        return user;
    }

    public void deleteUser(Long id) {
        findUser(id);

        repository.deleteById(id);
    }
}
