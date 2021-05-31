package com.codesoom.assignment.domain;

public class ProductFixtures {
    public static Product mouse() {
        return Product.builder()
                      .id(1L)
                      .name("쥐돌이")
                      .maker("냥이월드")
                      .price(5_000)
                      .build();
    }
}
