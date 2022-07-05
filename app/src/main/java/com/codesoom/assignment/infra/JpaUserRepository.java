package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> { }
