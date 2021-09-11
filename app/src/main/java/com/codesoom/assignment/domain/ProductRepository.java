package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

/**
 * 상품 컬렉션.
 * <p>
 * 상품 데이터에 접근 및 처리를 담당.
 */
public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
