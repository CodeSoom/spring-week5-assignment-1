package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;

public class ToyService implements ProductService {

    public ToyService(ProductRepository productRepository) {
    }

    @Override
    public Product create(ProductData productData) {
        return null;
    }
}
