package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;

public class UserApplicationService {
    public User createUser(String name, String mail, String password) {
        return new User();
    }
}
