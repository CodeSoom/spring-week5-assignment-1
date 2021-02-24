package com.codesoom.assignment.product.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 상품 정보.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    /** 상품 식별자. */
    @Id
    @GeneratedValue
    private Long id;

    /** 상품명. */
    private String name;

    /** 상품제조사. */
    private String maker;

    /** 상품가격. */
    @Embedded
    private Price price;

    /** 상품이미지. */
    private String imageUrl;

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    @Builder
    public Product(Long id, String name, String maker, BigDecimal price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = Price.of(price);
        this.imageUrl = imageUrl;
    }

    /**
     * 상품의 정보를 갱신합니다.
     */
    public void change(String name, String maker, BigDecimal price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = Price.of(price);
        this.imageUrl = imageUrl;
    }
}
