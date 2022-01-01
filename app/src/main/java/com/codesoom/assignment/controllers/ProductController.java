package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 목록을 리턴한다.
     *
     * @return 상품목록
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * id에 해당되는 상품정보를 리턴한다.
     *
     * @param productId 상품의 id
     * @return 찾는 상품
     */
    @GetMapping("{productId}")
    public Product detail(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    /**
     * productData에 상품을 생성하고 리턴한다.
     *
     * @param productData 생성된 상품 정보
     * @return 생성된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * id에 해당되는 상품을 수정하에 productData에 저장하고 리턴한다.
     *
     * @param productId 수정할 상품의 id
     * @param productData 수정한 상품 정보
     * @return 수정된 상품 정보
     */
    @PatchMapping("{productId}")
    public Product update(
            @PathVariable Long productId,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.updateProduct(productId, productData);
    }

    /**
     * id에 해당되는 상품을 삭제한다.
     *
     * @param productId 삭제할 상푸 id
     */
    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
