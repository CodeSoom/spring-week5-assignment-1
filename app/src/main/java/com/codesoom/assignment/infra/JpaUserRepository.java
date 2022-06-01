package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {
    Product save(Product product);

    Optional<User> findById(Long id);
}
