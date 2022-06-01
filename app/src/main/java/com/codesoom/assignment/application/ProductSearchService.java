package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductSearchService {
    List<Product> findProducts();

    Product findProduct(Long id);
}
