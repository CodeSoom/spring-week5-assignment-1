package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotNull
    @Mapping("email")
    private String email;

    @NotBlank
    @Mapping("password")
    private String password;

    public UserData(User user) {
        this(user.id(), user.name(), user.email(), user.password());
    }

    public static List<UserData> ofList(List<User> users) {
        return users.stream()
                .map(UserData::new)
                .collect(Collectors.toList());
    }
}
