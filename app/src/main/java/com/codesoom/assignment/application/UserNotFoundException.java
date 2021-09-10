package com.codesoom.assignment.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

/**
 * 사용자를 찾을 수 없는 경우 던집니다.
 */
@AllArgsConstructor
@Getter
public class UserNotFoundException extends NoSuchElementException {
    private final Long id;
}
