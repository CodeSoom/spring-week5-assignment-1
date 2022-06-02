package com.codesoom.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDataTest {

    @Test
    void testToString() {
        ProductData productData = ProductData.builder()
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .imageUrl("http://image.kyobobook.co.kr/newimages/giftshop_new/goods/400/1095/hot1602809707085.jpg")
                .build();

        assertThat(productData.toString())
                .isEqualTo("ProductData{" +
                        "name='" + productData.getName() + '\'' +
                        ", maker='" + productData.getMaker() + '\'' +
                        ", price=" + productData.getPrice() +
                        ", imageUrl='" + productData.getImageUrl() + '\'' +
                        '}');
    }
}
