package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("email")
    private String email;

    @Builder
    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
