package com.codesoom.assignment.product.service;

import com.codesoom.assignment.product.domain.Product;

import java.util.List;

public interface ProductService {
    /**
     * 제품 목록 전체를 반환한다.
     * @return 제품 목록 리스트
     */
    List<Product> getProducts();

    /**
     * 식별자와 일치하
     * @param id
     * @return
     */
    Product findProductById(Long id);
    Product addProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product deleteProductById(Long id);
}
