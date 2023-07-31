package com.codesoom.assignment.infra.user;

import com.codesoom.assignment.domain.user.User;
import com.codesoom.assignment.domain.user.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

@Primary
public interface JpaUserRepository extends
        UserRepository, CrudRepository<User, Long> {
}
