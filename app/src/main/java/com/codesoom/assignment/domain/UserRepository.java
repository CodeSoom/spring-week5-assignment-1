package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 유저를 조회, 저장, 삭제하는 저장소이다.
 */
public interface UserRepository {
    /** 주어진 아이디에 해당하는 유저를 조회한다. */
    Optional<User> findById(Long id);

    /** 주어진 유저를 저장하고 해당 객체를 리턴한다. */
    User save(User user);

    /** 주어진 유저를 삭제한다. */
    void delete(User user);
}
