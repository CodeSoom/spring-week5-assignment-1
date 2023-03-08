package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;


class ProductDataTest {

    @Test
    void builder() {
        ProductData productData = ProductData.builder()
                .id(1L)
                .name("name")
                .maker("maker")
                .price(1000)
                .imageUrl("test.co.kr")
                .build();

        String productDataString = ProductData.builder()
                .id(1L)
                .name("name")
                .maker("maker")
                .price(1000)
                .imageUrl("test.co.kr")
                .toString();

        assertThat(productData.getId()).isEqualTo(1L);
        assertThat(productData.getName()).isEqualTo("name");
        assertThat(productData.getMaker()).isEqualTo("maker");
        assertThat(productData.getPrice()).isEqualTo(1000);
        assertThat(productData.getImageUrl()).isEqualTo("test.co.kr");
        assertThat(productDataString).isNotNull();
    }
}