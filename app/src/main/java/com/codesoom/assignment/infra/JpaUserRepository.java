package com.codesoom.assignment.infra;

import org.springframework.data.repository.CrudRepository;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;

/**
 * 유저 정보를 저장하는 저장소입니다.
 */
public interface JpaUserRepository extends UserRepository, CrudRepository<User, Long> {

    User save(User user);

    void delete(User user);
}
