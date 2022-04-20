package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    default User save(UserData userData) {
        return save(User.from(userData));
    }
}
