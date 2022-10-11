package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    void deleteById(Long id);

    void deleteAll();
}
