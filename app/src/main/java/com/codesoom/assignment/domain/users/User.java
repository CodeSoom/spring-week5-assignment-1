package com.codesoom.assignment.domain.users;

import com.codesoom.assignment.dto.UserUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;

    private String password;

    public User replace(UserUpdateRequest replaceSource) {
        this.email = replaceSource.getEmail();
        this.name = replaceSource.getName();
        this.password = replaceSource.getPassword();
        return this;
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
        return Objects.equals(id, user.id)
                && Objects.equals(email, user.email)
                && Objects.equals(name, user.name)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, password);
    }
}
