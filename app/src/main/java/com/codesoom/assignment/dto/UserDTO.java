package com.codesoom.assignment.dto;

import com.codesoom.assignment.global.PassWordForm;
import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDTO {
    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUser {
        @NotBlank
        @Mapping("name")
        private String name;
        @NotBlank
        @Mapping("email")
        private String email;
        @PassWordForm
        @Mapping("password")
        private String password;
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateUser {
        @Mapping("name")
        @NotBlank
        private String name;

        @Mapping("email")
        @NotBlank
        private String email;

        @PassWordForm
        @Mapping("password")
        private String password;
    }
}
