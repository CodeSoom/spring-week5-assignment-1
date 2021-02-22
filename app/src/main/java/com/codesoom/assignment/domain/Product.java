package com.codesoom.assignment.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// 1. Entity (domain)
//  ** RDB의 Entity와 다름
// 2. JPA의 Entity 역할도 같이 함
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    private Integer price;

    private String image;

    public void change(Product source) {
        this.id = source.getId();
        this.name = source.getName();
        this.maker = source.getMaker();
        this.price = source.getPrice();
    }
}
