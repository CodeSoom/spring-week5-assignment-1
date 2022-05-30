package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * '생산자' Root Entity
 * <p>
 * All Known Extending Classes:
 * ToyProducer
 * </p>
 */

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Producer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
