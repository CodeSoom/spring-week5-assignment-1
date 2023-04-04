package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.UserModificationData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Builder.Default
    private boolean deleted = false;

    public void change(UserModificationData userModificationData) {
        this.name = userModificationData.getName();
        this.password = userModificationData.getPassword();
    }

    public void destory(){
        deleted = true;
    }
}
