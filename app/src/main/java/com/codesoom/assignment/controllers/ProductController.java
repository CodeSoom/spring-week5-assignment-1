package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품에 대한 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 모든 상품에 대한 요청을 처리합니다.
     *
     * @return 모든 상품
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 주어진 식별자를 가진 상품에 대한 요청을 처리합니다.
     *
     * @param id 상품 식별자
     * @return 상품
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 상품 생성에 대한 요청을 처리합니다.
     *
     * @param productData 생성할 상품 정보
     * @return 생성된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 주어진 식별자를 가진 상품에 대한 수정 요청을 처리합니다.
     *
     * @param id 상품 식별자
     * @param productData 수정할 상품 정보
     * @return 수정된 상품
     */
    @PatchMapping("{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 주어진 식별자를 가진 상품에 대한 삭제 요청을 처리합니다.
     *
     * @param id 상품 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
