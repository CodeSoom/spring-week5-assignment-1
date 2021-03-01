package com.codesoom.assignment.domain;


public interface UserRepository {

    User save(User user);

    User delete(Long id);
}
