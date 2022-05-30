package com.codesoom.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * '상품' Root Entity
 * <p>
 * All Known Extending Classes:
 * Toy
 * </p>
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @Embedded
    private Won price;

}
