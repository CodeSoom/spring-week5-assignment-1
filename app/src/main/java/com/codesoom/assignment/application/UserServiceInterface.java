package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInterface {

    public User findUser(Long id);

    public User createUser(User user);

    public User updateUser(Long id);

    public void deleteUser(Long id);
}
