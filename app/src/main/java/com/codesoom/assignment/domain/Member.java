package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phone;

    private boolean isGhost;

    public void update(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public boolean isGhost() {
        return isGhost;
    }

    @Builder
    public Member(Long id, String name, String phone, boolean isGhost) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.isGhost = isGhost;
    }
}
