package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

public interface UserService {

    User createUser(UserData source);

    User updateUser(Long id, UserData source);

    void deleteUser(Long id);

}
