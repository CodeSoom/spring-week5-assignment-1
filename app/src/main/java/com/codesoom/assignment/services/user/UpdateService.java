package com.codesoom.assignment.services.user;

import com.codesoom.assignment.services.user.domain.User;

public interface UpdateService {
    User execute(Long id, String name, String email, String password);
}
