package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품 관련 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * 등록된 상품 목록 조회 요청을 처리합니다.
     *
     * @return 등록된 상품 목록
     */

    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 상품 조회 요청을 처리하고, 해당 상품 정보를 반환합니다.
     *
     * @param id
     * @return 해당 상품 정보
     */

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 상품 등록 요청을 처리하고, 등록된 상품 정보를 반환합니다.
     *
     * @param productData
     * @return 등록된 상품 정보
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 상품 정보 수정 요청을 처리하고, 수정된 상품 정보를 반환합니다.
     *
     * @param id
     * @param productData
     * @return
     */

    @RequestMapping(value = "{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public Product update(@PathVariable Long id, @RequestBody @Valid ProductData productData) {
        return productService.updateProduct(id, productData);
    }

    /**
     * 상품 삭제 요청을 처리합니다.
     *
     * @param id
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
