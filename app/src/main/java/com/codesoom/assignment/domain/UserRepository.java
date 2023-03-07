package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {


    User save(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
