package com.codesoom.assignment.infra;

import org.springframework.data.repository.CrudRepository;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

public interface JpaUserRepository extends UserRepository, CrudRepository<User, Long> {

    User save(User user);

    void delete(User user);
}
