package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductDataTest {

    @Test
    void setImageUrl(){
        ProductData productData = new ProductData();
        productData.setImageUrl("https://pixabay.com/photos/mouse-rodent-cute-mammal-nager-1708379/");

        assertThat(productData.getImageUrl()).isEqualTo("https://pixabay.com/photos/mouse-rodent-cute-mammal-nager-1708379/");
    }

    @Test
    void builder() {
        assertThat(ProductData.builder().toString())
                .isEqualTo("ProductData.ProductDataBuilder(name=null, maker=null, price=null, imageUrl=null)");
    }
}