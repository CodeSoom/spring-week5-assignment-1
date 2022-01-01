package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User,Long> {

    User save(User user);

    boolean existsByEmail(String email);

}
