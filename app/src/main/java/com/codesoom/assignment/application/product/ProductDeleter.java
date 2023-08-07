package com.codesoom.assignment.application.product;

import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductDeleter {

    private final ProductRepository productRepository;
    private final ProductReader productReader;

    public ProductDeleter(ProductRepository productRepository, ProductReader productReader) {
        this.productRepository = productRepository;
        this.productReader = productReader;
    }

    public Product deleteProduct(Long id) {
        Product product = productReader.getProduct(id);

        productRepository.delete(product);

        return product;
    }
}
