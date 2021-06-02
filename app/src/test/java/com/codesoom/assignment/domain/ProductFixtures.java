package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductData;

public class ProductFixtures {
    public static Product mouseDol() {
        return Product.builder()
                      .id(1L)
                      .name("쥐돌이")
                      .maker("냥이월드")
                      .price(5_000)
                      .imageUrl("https://cdn.pixabay" +
                                        ".com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg")
                      .build();
    }

    public static ProductData dataMouseDol() {
        return ProductData.builder()
                          .name("쥐돌이")
                          .maker("냥이월드")
                          .price(5_000)
                          .imageUrl("https://cdn.pixabay" +
                                            ".com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg")
                          .build();
    }

    public static Product mouseSunWithoutImageUrl() {
        return Product.builder()
                      .id(2L)
                      .name("쥐순이")
                      .maker("코드숨")
                      .price(10_000)
                      .build();
    }

    public static Product mouseSunWithImageUrl() {
        return Product.builder()
                      .id(2L)
                      .name("쥐순이")
                      .maker("코드숨")
                      .price(10_000)
                      .imageUrl("http://localhost:8080/rat")
                      .build();
    }

    public static ProductData dataMouseSun() {
        return ProductData.builder()
                          .name("쥐순이")
                          .maker("냥이세계")
                          .price(6_000)
                          .imageUrl("http://localhost:8080/test")
                          .build();
    }
}
