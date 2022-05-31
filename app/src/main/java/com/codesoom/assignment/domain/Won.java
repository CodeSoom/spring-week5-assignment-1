package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Won extends Money {
    private BigDecimal value;
}
