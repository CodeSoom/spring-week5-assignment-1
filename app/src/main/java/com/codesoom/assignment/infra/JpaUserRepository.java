package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {

    List<User> findAll();

}
