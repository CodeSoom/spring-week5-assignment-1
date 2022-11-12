package com.codesoom.assignment.user.application.in.command;

import com.codesoom.assignment.user.domain.User;

public interface UserUpdateRequest {

    String getName();

    String getEmail();

    String getPassword();

    User toEntity();
}
