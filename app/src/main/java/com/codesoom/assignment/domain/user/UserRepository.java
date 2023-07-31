package com.codesoom.assignment.domain.user;

import com.codesoom.assignment.domain.product.Product;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    void delete(User user);

    void deleteAll();
}
