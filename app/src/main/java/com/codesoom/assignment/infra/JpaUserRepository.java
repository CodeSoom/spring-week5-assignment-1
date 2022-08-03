package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository extends UserRepository, CrudRepository<Long, User> {
}
