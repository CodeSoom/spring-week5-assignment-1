package com.codesoom.assignment.user.adapter.out.persistence;

import com.codesoom.assignment.user.application.out.UserRepository;
import com.codesoom.assignment.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserPersistenceAdapter
        extends UserRepository, JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);
}
