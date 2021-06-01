package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    public User chageData(UserData userData){
        this.email = userData.getEmail();
        this.name = userData.getName();
        this.password = userData.getPassword();
        return this;
    }
}
