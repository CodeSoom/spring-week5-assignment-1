package com.codesoom.assignment.product.infrastructure;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {
}
