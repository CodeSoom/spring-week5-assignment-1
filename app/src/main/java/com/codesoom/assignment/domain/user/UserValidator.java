package com.codesoom.assignment.domain.user;

import com.codesoom.assignment.dto.user.UserData;

public interface UserValidator {
    void validateRegisterUserRequest(UserData.RegisterUserRequest request);
    void validateUpdateUserRequest(UserData.UpdateUserRequest owner);
}
