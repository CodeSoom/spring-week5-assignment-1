package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

/**
 * User 리소스 비즈니스 로직과 데이터베이스를 연결한다.
 *
 * @see User
 * @see UserRepository
 */
@Primary
public interface JpaUserRepository
    extends UserRepository, CrudRepository<User, Long> {
    User save(User user);

    void delete(User user);
}
