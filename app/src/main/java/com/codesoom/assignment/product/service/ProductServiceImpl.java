package com.codesoom.assignment.product.service;

import com.codesoom.assignment.product.exception.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product foundProduct = findProductById(id);
        foundProduct.update(product);
        return foundProduct;
    }

    public Product deleteProductById(Long id) {
        Product foundProduct = productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(id);
        return foundProduct;
    }
}
