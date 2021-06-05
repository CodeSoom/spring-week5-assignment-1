package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품 API의 HTTP 요청을 처리합니다.
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    /**
     * 생성자.
     *
     * @param productService 상품 서비스 객체
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 모든 상품 목록을 응답합니다.
     *
     * @return 모든 상품 목록
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 상품을 찾고, 찾은 상품을 응답합니다.
     *
     * @param id 찾을 상품 id
     * @return 200 상태 코드와 찾은 상품
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 상품을 생성하고, 201 상태 코드와 생성한 상품을 응답합니다.
     *
     * @param productData 생성할 상품 정보
     * @return 201 상태 코드와 생성한 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 상품을 갱신하고, 갱신한 상품을 응답합니다.
     *
     * @param id 갱신할 상품 id
     * @param productData 갱신할 내용
     * @return 201 상태 코드와 갱신한 상품
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 상품을 삭제하고, 204 상태코드를 응답합니다.
     *
     * @param id 삭제할 상품 id
     * @throws ProductNotFoundException 상품을 찾지 못한 경우
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
