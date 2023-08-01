package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JpaTest {
    @Autowired
    public UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
