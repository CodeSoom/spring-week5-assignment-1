package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

/**
 * 상품 관련 DB 처리를 담당합니다.
 */
public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
