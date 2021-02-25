package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "id")
public class UserData {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "name 값은 필수입니다")
    private String name;

    @NotBlank(message = "email 값은 필수입니다")
    private String email;

    @NotBlank(message = "password 값은 필수입니다")
    private String password;

    @Builder
    public UserData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /** 사용자 정보를 Entity로 만든다 */
    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
