package com.codesoom.assignment.domain;

import java.math.BigDecimal;

public class Toy implements Product {

    private Long id;
    private String name;
    private String maker;
    private BigDecimal price;
    private String imagePath;

    public Toy(String name, String maker, BigDecimal price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Toy(Long id, String name, String maker, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Toy(Long id, String name, String maker, BigDecimal price, String imagePath) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imagePath = imagePath;
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
        return imagePath;
    }
}
