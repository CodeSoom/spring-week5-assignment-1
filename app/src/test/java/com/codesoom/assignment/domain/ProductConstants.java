package com.codesoom.assignment.domain;

import java.util.List;

import com.google.common.collect.Lists;

public interface ProductConstants {
    final Long ID = 1L;
    final String NAME = "쥐돌이";
    final String MAKER = "냥이월드";
    final Integer PRICE = 1000;
    final String IMAGE_URL = "http://localhost:8080/rat";
    final Product PRODUCT = new Product(ID, NAME, MAKER, PRICE, IMAGE_URL);
    final List<Product> PRODUCT_LIST = Lists.newArrayList(PRODUCT);
    final List<Product> EMPTY_LIST = Lists.newArrayList();
}
