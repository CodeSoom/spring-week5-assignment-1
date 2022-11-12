package com.codesoom.assignment.user.application.out;


import com.codesoom.assignment.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User product);

    void delete(User product);
}
