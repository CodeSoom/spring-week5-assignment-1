package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @Mapping("name")
    private String name;
    @Mapping("email")
    private String email;
    @Mapping("password")
    private String password;
}
