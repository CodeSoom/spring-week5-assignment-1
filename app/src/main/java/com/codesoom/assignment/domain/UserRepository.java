package com.codesoom.assignment.domain;

import java.util.Optional;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * 유저 저장소.
 */
@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

    User save(User user);

    boolean existsById(Long id);

    Optional<User> findById(Long id);
}
