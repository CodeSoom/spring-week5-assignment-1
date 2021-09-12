package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 사용자 데이터를 다루는 기본 명령을 정의합니다.
 */
@Primary
public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {
    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);
}
