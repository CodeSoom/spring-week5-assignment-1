package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * User JPA 데이터 저장 장소
 */
@Primary
public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {

    User save(User user);

    Optional<User> findById(Long id);

    void delete(User user);
}
