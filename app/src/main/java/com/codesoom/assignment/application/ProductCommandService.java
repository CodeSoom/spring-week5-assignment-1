package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;

import java.util.Collection;

/**
 * 상품에 변형을 주는 기능을 제공합니다.
 */
public interface ProductCommandService {
    /**
     * 상품을 생성하고 리턴한다.
     *
     * @param productData 상품 정보
     * @return 상품
     */
    Product create(ProductData productData);

    /**
     * 상품을 찾아 제거한다.
     *
     * @param id 식별자
     */
    void deleteById(Long id);
}
