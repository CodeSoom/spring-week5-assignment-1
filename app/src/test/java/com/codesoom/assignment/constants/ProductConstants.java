package com.codesoom.assignment.constants;

import java.util.List;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import com.google.common.collect.Lists;

/**
 * Product관련 테스트에서 반복적으로 사용되는 데이터 정의
 */
public interface ProductConstants {
    final Long ID = 1L;
    final String NAME = "쥐돌이";
    final String MAKER = "냥이월드";
    final Integer PRICE = 1000;
    final String IMAGE_URL = "http://localhost:8080/rat";
    final Product PRODUCT = Product.builder()
        .name(NAME)
        .maker(MAKER)
        .price(PRICE)
        .imageUrl(IMAGE_URL)
        .build();
    final ProductData PRODUCT_DATA = new ProductData(null, NAME, MAKER, PRICE, IMAGE_URL);
    final List<Product> PRODUCT_LIST = Lists.newArrayList(PRODUCT);
    final List<Product> EMPTY_LIST = Lists.newArrayList();
}
