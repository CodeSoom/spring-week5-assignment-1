package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.dto.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;

import java.util.Collection;

/**
 * 상품의 조회 기능을 제공합니다.
 */
public interface ProductQueryService {
    /**
     * 상품을 찾아 리턴한다.
     *
     * @param id 식별자
     * @return 상품
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    Product findById(Long id);

    /**
     * 판매중인 상품 목록을 리턴합니다.
     *
     * @return 판매중인 상품 목록
     */
    Collection<Product> findAll();

    /**
     * 품절된 상품 목록을 리턴합니다.
     *
     * @return 품절된 상품 목록
     */
    Collection<Product> findAllSoldOut();
}
