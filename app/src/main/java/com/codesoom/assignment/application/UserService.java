package com.codesoom.assignment.application;

import com.codesoom.assignment.UserNotFoundException;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserData userData) {
        return userRepository.save(userData.toUser());
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User updateUser(Long id, UserData source) {
        User user = findUser(id);

        user.update(source.toUser());

        return user;
    }

    public User deleteUser(Long id) {
        User user = findUser(id);

        userRepository.delete(user);

        return user;
    }

    public void clearData() {
        userRepository.deleteAll();
    }
}
