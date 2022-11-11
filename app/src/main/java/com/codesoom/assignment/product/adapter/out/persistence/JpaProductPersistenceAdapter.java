package com.codesoom.assignment.product.adapter.out.persistence;

import com.codesoom.assignment.product.application.port.out.ProductRepository;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProductPersistenceAdapter
        extends ProductRepository, JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
