package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

/**
 * Product 데이터 저장소.
 */
public interface ProductRepository {
    /**
     * 저장된 모든 product를 반환합니다.
     *
     * @return 저장된 모든 product 집합
     */
    List<Product> findAll();

    /**
     * 주어진 id와 일치하는 product를 반환합니다.
     *
     * @param id product 식별자
     * @return
     */
    Optional<Product> findById(Long id);

    /**
     * 주어진 product를 저장하고 반환합니다.
     *
     * @param product 저장하고자 하는 product
     * @return 저장된 product
     */
    Product save(Product product);

    /**
     * 주어진 product를 삭제합니다.
     *
     * @param product 삭제하고자 하는 product
     */
    void delete(Product product);
}
