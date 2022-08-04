package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;

import java.util.Collection;

/**
 * 상품에 변형을 주는 기능을 제공합니다.
 */
public interface ProductCommandService {
    Product create(ProductData productData);
}
