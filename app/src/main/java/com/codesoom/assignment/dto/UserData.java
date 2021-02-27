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
<<<<<<< HEAD
=======
    @NotBlank(message = "email 값은 필수입니다")
>>>>>>> 3c21da3 (Revise feeback)
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

    /** 사용자 정보를 Entity로 만든다 */
//    public User toEntity() {
//        return User.builder()
//                .name(this.name)
//                .email(this.email)
//                .password(this.password)
//                .build();
//    }
}
