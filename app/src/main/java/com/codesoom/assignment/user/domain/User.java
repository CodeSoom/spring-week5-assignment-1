package com.codesoom.assignment.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void update(User updateUser) {
        updateName(updateUser.getName());
        updateEmail(updateUser.getEmail());
        updatePassword(updateUser.getPassword());
    }

    private void updateName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    private void updateEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    private void updatePassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
