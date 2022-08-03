package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;

import java.util.Collection;

public interface ProductService {
    Product create(ProductData productData);

    Product findById(Long id);

    Collection<Product> findAll();
}
