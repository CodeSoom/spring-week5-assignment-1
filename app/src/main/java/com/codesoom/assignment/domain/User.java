package com.codesoom.assignment.domain;

import com.codesoom.assignment.Exceptions.UserErrorMessage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Entity
public class User {

    @NotBlank(message = "The username must contain at least one non-whitespace character.")
    @NotNull(message = "The U")
    @Id
    private String username;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @Builder
    public User(String username, String password, String email) {
        Assert.notNull(email, UserErrorMessage.EMAIL_NOT_NULL);
        Assert.hasText(email, UserErrorMessage.EMAIL_NOT_BLANK);


        this.username = username;
        this.password = password;
        this.email = email;
    }

    protected User() {
    }
}
