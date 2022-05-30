package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * '돈' Root Value Object
 * <p>
 * All Known Extending Classes:
 * Won
 * </p>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Money {
    /**
     * '돈'의 가치
     */
    private BigDecimal value;
}
