package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.users.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserUpdateDto implements UserUpdateRequest {

    private String email;

    private String name;

    private String password;
}
