package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

@Primary
public interface JpaUserRepository extends UserRepository, CrudRepository<User, Long> {

    User save(User user);

    boolean existsById(Long id);

    Optional<User> findById(Long id);

    void deleteAll();
}
