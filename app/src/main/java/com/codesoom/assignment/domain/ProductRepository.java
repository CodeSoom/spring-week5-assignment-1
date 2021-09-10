package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

/**
 * 상품 데이터를 다루는 명령을 정의합니다.
 */
public interface ProductRepository {
    /**
     * 상품 목록을 반환합니다.
     * @return 상품 목록
     */
    List<Product> findAll();

    /**
     * 해당 식별자의 상품을 반환합니다.
     * @param id 상품 식별자
     * @return 상품
     */
    Optional<Product> findById(Long id);

    /**
     * 상품을 저장하고 반환합니다.
     * @param product 생성할 상품
     * @return 생성된 상품
     */
    Product save(Product product);

    /**
     * 상품을 삭제합니다.
     * @param product 삭제할 상품
     */
    void delete(Product product);
}
