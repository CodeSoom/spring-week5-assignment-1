package com.codesoom.assignment.domain.entities;

import com.codesoom.assignment.domain.vos.Won;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @Embedded
    private Won price;

    public Product(Long id, String name, Won price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(String name, Won price) {
        this.name = name;
        this.price = price;
    }

}
