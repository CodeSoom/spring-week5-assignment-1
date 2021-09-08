package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

/**
 * Product 리소스 비즈니스 로직을 정의한다.
 */
public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
