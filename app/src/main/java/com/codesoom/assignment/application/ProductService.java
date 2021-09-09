package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;

import java.util.List;

/**
 * 상품 조회, 수정, 삭제 서비스
 */
public interface ProductService {

    /**
     * 상품들을 조회
     * @return 상품 배열을 리턴합니다.
     */
    List<Product> getProducts();

    /**
     * 상품 상세 조회
     * @param id 조회할 상품의 id
     * @return 상품을 리턴합니다.
     */
    Product getProduct(Long id);

    /**
     * 상품을 등록
     * @param source 등록할 상품의 내용
     * @return 등록한 상품을 리턴합니다.
     */
    Product createProduct(ProductData source);

    /**
     * 상품 수정
     * @param id 수정할 상품의 id
     * @param source 상품의 수정 내용
     * @return 수정한 상품을 리턴합니다.
     */
    Product updateProduct(Long id, ProductData source);

    /**
     * 상품 삭제
     * @param id 삭제할 상품의 id
     */
    void deleteProduct(Long id);

}

