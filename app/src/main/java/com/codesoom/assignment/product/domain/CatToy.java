package com.codesoom.assignment.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CatToy {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @PositiveOrZero
    private Integer price;

    private String imageUrl;

    public CatToy(String name, String maker, int price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void update(CatToy catToy) {
        this.name = catToy.getName();
        this.maker = catToy.getMaker();
        this.price = catToy.getPrice();
        this.imageUrl = catToy.getImageUrl();
    }
}