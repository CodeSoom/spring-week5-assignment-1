package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.dto.UserData;

public interface UserService {

    User createUser(UserData userData);

}
