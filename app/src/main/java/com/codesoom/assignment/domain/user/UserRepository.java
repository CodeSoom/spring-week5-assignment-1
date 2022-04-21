package com.codesoom.assignment.domain.user;

import com.codesoom.assignment.dto.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    void delete(long userId);
}
