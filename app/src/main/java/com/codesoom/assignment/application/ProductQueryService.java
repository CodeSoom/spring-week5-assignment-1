package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;

import java.util.Collection;

/**
 * 상품의 조회 기능을 제공합니다.
 */
public interface ProductQueryService {
    /**
     * 식별자를 가진 상품을 리턴한다.
     *
     * @param id 식별자
     * @return 상품
     * @throws ProductNotFoundException 식별자를 가진 상품이 없으면 예외를 던진다
     */
    Product findById(Long id);

    /**
     * 상품 목록을 리턴합니다.
     *
     * @return 상품 목록
     */
    Collection<Product> findAll();
}
