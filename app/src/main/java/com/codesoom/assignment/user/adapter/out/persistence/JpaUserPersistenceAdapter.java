package com.codesoom.assignment.user.adapter.out.persistence;

import com.codesoom.assignment.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserPersistenceAdapter
        extends JpaRepository<User, Long> {
}
