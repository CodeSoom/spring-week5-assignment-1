package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품 관련 http 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 목록 요청을 받아 조회하고 리턴합니다.
     * @return 상품 목록
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 상품 상세정보 요청을 받아 조회하고 리턴합니다.
     * @param id 조회할 상품 id
     * @return 조회한 상품
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 새 상품 등록 요청을 받아 등록하고 리턴합니다.
     * @param productData 등록할 상품내용
     * @return 등록한 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 상품 수정 요청을 받아 업데이트하고 리턴합니다.
     * @param id 업데이트할 상품 id
     * @param productData 업데이트할 상품내용
     * @return 업데이트한 상품내용
     */
    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 상품 삭제 요청을 받아 삭제합니다.
     * @param id 삭제할 상품 id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
