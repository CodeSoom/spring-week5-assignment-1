package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품에 대한 요청을 한다.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 전체 상품 목록을 리턴한다.
     * 
     * @return 저장되어 있는 전체 상품 목록
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 주어진 식별자에 해당하는 상품을 리턴한다.
     * 
     * @param id - 조회하고자 하는 상품의 식별자
     * @return 주어진 식별자에 해당하는 상품
     */
    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 주어진 상품을 저장하고 해당 객체를 리턴한다.
     *
     * @param productData - 저장하고자 하는 상품
     * @return 저장 된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 주어진 식별자에 해당하는 상품을 수정하고 해당 객체를 리턴한다.
     *
     * @param id - 수정하고자 하는 상품의 식별자
     * @param productData - 수정할 새로운 상품
     * @return 수정 된 상품
     */
    @PatchMapping("/{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 주어진 식별자에 해당하는 상품을 삭제하고 해당 객체를 리턴한다.
     *
     * @param id - 삭제하고자 하는 상품의 식별자
     * @return 삭제 된 상품
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product delete(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
