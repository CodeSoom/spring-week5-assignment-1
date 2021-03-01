package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDataDummyTest {
    private final ProductData givenProductData = new ProductData.ProductDataBuilder()
            .id(1L)
            .imageUrl("none")
            .build();

    @Test
    void setId() {
        givenProductData.setId(1L);
    }

    @Test
    void setImageUrl() {
        givenProductData.setImageUrl("http://image.com/");
    }

    @Test
    void getId() {
        givenProductData.setId(1L);

        assertThat(givenProductData.getId()).isEqualTo(1L);
    }

    @Test
    void toStringTest() {
        final Long id = 1L;
        final String productString = new ProductData.ProductDataBuilder()
                .id(id)
                .toString();

        assertThat(productString).contains(id.toString());
    }
}
