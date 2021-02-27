package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private Product product;
    private final Long PRODUCT_ID = 1L;
    private final String PRODUCT_NAME = "쥐돌이";
    private final String PRODUCT_MAKER = "냥이월드";
    private final Integer PRODUCT_PRICE = 5000;
    private final String PRODUCT_IMAGEURL = "https://http.cat/200";

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imageUrl(PRODUCT_IMAGEURL)
                .build();
    }

    @Test
    @DisplayName("모든 매개변수를 가지는 상품 Builder 검사")
    void BuilderWithAllArgs() {
        assertThat(product.getId()).isEqualTo(PRODUCT_ID);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGEURL);
    }

    @Test
    @DisplayName("아이디를 제외한 모든 매개변수를 가지는 상품 Builder 검사")
    void BuilderWithArgsWithoutId() {
        Product product = Product.builder()
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imageUrl(PRODUCT_IMAGEURL)
                .build();

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGEURL);
    }

    @Test
    @DisplayName("어떤 매개변수도 가지지 않는 상품 Builder 검사")
    void BuilderWithoutArgs() {
        Product product = Product.builder()
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imageUrl(PRODUCT_IMAGEURL)
                .build();

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGEURL);
    }

//    @Test
//    @DisplayName("모든 변수의 값을 리턴하는 ToString 검사")
//    void CheckToStringWithAllVariables() {
//        String stringValues = product.toString();
//
//        assertThat(stringValues)
//                .contains(String.valueOf(PRODUCT_ID))
//                .contains(PRODUCT_NAME)
//                .contains(PRODUCT_MAKER)
//                .contains(String.valueOf(PRODUCT_PRICE))
//                .contains(PRODUCT_IMAGEURL);
//    }
}
