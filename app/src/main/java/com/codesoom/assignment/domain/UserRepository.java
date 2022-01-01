package com.codesoom.assignment.domain;

import com.codesoom.assignment.application.UserService;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);
}
