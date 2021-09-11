package com.codesoom.assignment.domain;

import java.util.Optional;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * 회원 컬렉션.
 * <p>
 * 회원 정보에 접근 및 처리 담당.
 */
@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

    User save(User user);

    boolean existsById(Long id);

    Optional<User> findById(Long id);

    void deleteAll();

    void delete(User user);
}
