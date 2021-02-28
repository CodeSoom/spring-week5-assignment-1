package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import com.github.dozermapper.core.Mapping;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "id")
public class UserData {
    private Long id;

    @Mapping("name")
    @NotBlank(message = "name 값은 필수입니다")
    private String name;

    @Mapping("email")
    @NotBlank(message = "email 값은 필수입니다")
    private String email;

    @Mapping("password")
    @NotBlank(message = "password 값은 필수입니다")
    private String password;

    @Builder
    public UserData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UserData))
            return false;

        UserData userData = (UserData) o;

        return (userData.name).equals(this.name) && (userData.email).equals(this.email)
                && (userData.password).equals(this.password);
    }

    public boolean isNameWrong() {
        return this.name != null && this.name.equals("");
    }

    public boolean isEmailWrong() {
        return this.email != null && this.email.equals("");
    }

    public boolean isPasswordWrong() {
        return this.password != null && this.password.equals("");
    }

    /** 사용자 정보를 Entity로 만든다 */
//    public User toEntity() {
//        return User.builder()
//                .name(this.name)
//                .email(this.email)
//                .password(this.password)
//                .build();
//    }
}
