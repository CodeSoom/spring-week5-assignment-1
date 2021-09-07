package com.codesoom.assignment.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Getter
public class UserNotFoundException extends NoSuchElementException {
    private final Long id;
}
