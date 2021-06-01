package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Primary
public interface JpaUserRepository extends UserRepository, JpaRepository<User,Long> {
    User findUserById(Long id);

    List<User> findAll();

    User save(User user);

    void deleteUserById(Long id);
}
