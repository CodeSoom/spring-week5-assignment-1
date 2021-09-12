package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
public class User {
    private String Email;
}
