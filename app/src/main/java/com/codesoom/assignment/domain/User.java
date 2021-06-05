package com.codesoom.assignment.domain;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Getter;
=======
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
<<<<<<< HEAD
=======
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
@Builder
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

<<<<<<< HEAD
    private User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    protected User() {
    }
=======
>>>>>>> 22c8bd2609d69f3ecd808fca4d6127023b3f2971
}
