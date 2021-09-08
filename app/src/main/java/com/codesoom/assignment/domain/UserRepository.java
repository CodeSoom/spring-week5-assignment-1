package com.codesoom.assignment.domain;

public interface UserRepository {
    User save(User user);

    void delete(User user);
}
