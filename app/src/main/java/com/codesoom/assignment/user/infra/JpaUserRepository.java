package com.codesoom.assignment.user.infra;

import com.codesoom.assignment.user.domain.User;
import com.codesoom.assignment.user.domain.UserRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자의 정보를 관리합니다.
 */
public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);

    void deleteAll();
}
