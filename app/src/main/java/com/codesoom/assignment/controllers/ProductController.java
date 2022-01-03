package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 상품에 대한 HTTP 요청의 처리를 담당한다.
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품목록을 리턴한다.
     *
     * @return 상품목록
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 요청된 id의 상품을 리턴한다.
     *
     * @param id 상품id
     * @return 해당하는 상품
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 상품을 저장하고 리턴한다.
     *
     * @param productData 저장할 상품
     * @return 저장된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductData productData) {
        return productService.createProduct(productData);
    }

    /**
     * 요청받은 id의 상품을 수정한다
     *
     * @param productData 상품의 수정된 정보
     * @param id      수정할 상품의 id
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
     * 요청받은 id의 상품을 삭제한다.
     *
     * @param id 삭제할 상품의 id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
