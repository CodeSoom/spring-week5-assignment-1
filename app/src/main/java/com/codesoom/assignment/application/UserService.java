package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.UserData;
import com.codesoom.assignment.infra.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public UserData create(UserData userData) {
        return null;
    }
}
