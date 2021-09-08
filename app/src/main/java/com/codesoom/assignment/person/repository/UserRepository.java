package com.codesoom.assignment.person.repository;

import com.codesoom.assignment.person.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User saveUser(User user);
    Optional<User> findById(Long id);
    void delete(Long id);
}
