package com.codesoom.assignment.domain;

import java.util.List;

public interface UserRepository {
    User findUserById(Long id);

    List<User> findAll();

    User save(User user);

    void deleteUserById(Long id);
}
