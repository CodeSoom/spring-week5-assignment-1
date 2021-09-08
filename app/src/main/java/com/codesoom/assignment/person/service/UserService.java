package com.codesoom.assignment.person.service;

import com.codesoom.assignment.person.domain.User;

public interface UserService {
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
