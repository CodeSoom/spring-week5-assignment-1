package com.codesoom.assignment.domain;

import java.math.BigDecimal;

public interface Product {
    Long id();

    String name();

    String maker();

    BigDecimal price();

    String imagePath();
}
