package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long aLong);
    void delete(User user);
}
