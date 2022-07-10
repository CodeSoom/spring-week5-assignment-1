package com.codesoom.assignment.services.user.infra;

import com.codesoom.assignment.services.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> { }
