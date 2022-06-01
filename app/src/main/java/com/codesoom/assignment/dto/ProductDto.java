package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto implements Product {

    private Long id;
    private String name;
    private String maker;
    private BigDecimal price;
    private String imagePath;

    public ProductDto(Long id, String name, String maker, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    @Override
    public Long id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String maker() {
        return this.maker;
    }

    @Override
    public BigDecimal price() {
        return this.price;
    }

    @Override
    public String imagePath() {
        return this.imagePath;
    }
}
