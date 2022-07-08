package com.codesoom.assignment.services.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    // TODO: 의문 - ProductRepository interface와 JpaProductRepository interface는 어떻게 연결되어야 할까?
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
